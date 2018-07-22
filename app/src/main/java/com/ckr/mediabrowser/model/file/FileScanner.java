package com.ckr.mediabrowser.model.file;

import android.app.Activity;
import android.content.Context;
import android.os.storage.StorageManager;
import android.text.TextUtils;
import android.util.Log;

import com.ckr.mediabrowser.presenter.file.OnFileScanListener;
import com.ckr.mediabrowser.view.MediaBrowserApplication;

import java.io.File;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingDeque;

import static com.ckr.mediabrowser.util.MediaLog.Logd;
import static com.ckr.mediabrowser.util.MediaLog.Logw;

/**
 * Created by PC大佬 on 2018/5/27.
 */

public class FileScanner {
	private static final String TAG = "FileScanner";
	public static final int IDLE = 0;    //闲置状态
	public static final int SCANNING = 1;    //扫码状态
	volatile static int status = IDLE;
	private static final int MINIMAL_FILE_SIZE = 5 * 1024;    // 过滤掉文件大小小于5kb的文件
	private static final int QUICK_SHOW_NUM = 10;    //快速显示的阈值
	private static ExecutorService service;    //线程池
	private static Map<String, List<Document>> map = new ConcurrentHashMap<>();    //key：第一级目录的路径,value：目录对应的所有文件
	private volatile static Map<String, List<Document>> documents = new HashMap<>();
	private static List<Callable<String>> tasks = new ArrayList<>();    //存放所有任务
	private static List<String> files = Arrays.asList(".pdf", ".docx", ".doc", ".xlsx", ".xls", ".pptx", ".ppt"/*, ".txt"*/);
	static BlockingQueue<Future<String>> queue;
	static CompletionService<String> completionService;
	static OnFileScanListener mOnFileScanListener;
	static int threadSum;
	static boolean first;
	static final boolean FILTER = false;

	public static int getStatus() {
		return status;
	}

	/**
	 * 遍历所有的挂载点
	 *
	 * @return List<File>
	 */
	public static List<File> getStorageDirectories() {
		List<File> list = new ArrayList<>();
		long timeMillis = System.currentTimeMillis();
		Logd(TAG, "getStorageDirectories: 开始时间：" + timeMillis);
		String[] volumePaths = getVolumePaths(MediaBrowserApplication.getContext());//红米note4x 耗时3ms
		Logd(TAG, "getStorageDirectories: 结束时间：" + (System.currentTimeMillis() - timeMillis));
		Logd(TAG, "getStorageDirectories: 遍历所有的挂载点:" + Arrays.toString(volumePaths));
		for (int i = 0; i < volumePaths.length; i++) {
			File file = new File(volumePaths[i]);
			list.add(file);
		}
		return list;
	}

	/**
	 * 枚举所有挂载点
	 */
	private static String[] getVolumePaths(Context context) {
		String[] paths = null;
		StorageManager mStorageManager;
		Method mMethodGetPaths = null;
		try {
			mStorageManager = (StorageManager) context
					.getSystemService(Activity.STORAGE_SERVICE);
			mMethodGetPaths = mStorageManager.getClass().getMethod(
					"getVolumePaths");
			paths = (String[]) mMethodGetPaths.invoke(mStorageManager);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return paths;
	}

	/**
	 * 忽略特定目录
	 *
	 * @param name
	 * @return
	 */
	private static String ignoreDirectory(String name) {
		if (TextUtils.isEmpty(name))
			return "";
		if (name.equals(".")
				|| name.equals("..")
				|| name.equalsIgnoreCase("Android")
				|| name.equalsIgnoreCase("LOST.DIR")
				|| name.equalsIgnoreCase("tencentmapsdk")
				|| name.equalsIgnoreCase("taobao")
				|| name.equalsIgnoreCase("alipay")
				|| name.equalsIgnoreCase("navione")
				|| name.equalsIgnoreCase("picstore")
				|| name.equalsIgnoreCase("qzone")
				|| name.equalsIgnoreCase("tencent")
				|| name.equalsIgnoreCase("brut.googlemaps")
				|| name.startsWith(".")) {
			return "";
		}
		return name;
	}

	public static void scanFile(OnFileScanListener listener) {
		if (service != null) {
			return;
		}
		List<File> directories = getStorageDirectories();
		if (directories.isEmpty())
			return;
		mOnFileScanListener = listener;
		int processors = Runtime.getRuntime().availableProcessors() * 2 + 2;
		service = Executors.newFixedThreadPool(processors);
		queue = new LinkedBlockingDeque<Future<String>>();
		completionService = new ExecutorCompletionService<String>(
				service, queue);
		map.clear();
		tasks.clear();
		documents.clear();
		Long startTime = System.currentTimeMillis();
		threadSum = 0;
		first = true;
		status = SCANNING;
		for (int i = 0; i < directories.size(); i++) {
			String path = directories.get(i).getAbsolutePath();
			if (TextUtils.isEmpty(path)) {
				continue;
			}
			File root = new File(path);
			Logd(TAG, "scanFile,线程数：" + processors + ",挂载点路径：" + path);
			if (root.isDirectory()) {
				final File[] files = root.listFiles();
				if (files == null || files.length == 0) {
					continue;
				}
				List<Document> d = new ArrayList<>();
				for (final File file : files) {
					if (file.isDirectory()) {
						if (TextUtils.isEmpty(ignoreDirectory(file.getName()))) {
							continue;
						}
						threadSum++;
						try {
							completionService.submit(new Callable<String>() {
								@Override
								public String call() throws Exception {
									List<Document> fileList = new ArrayList<>();
									String folderPath = file.getAbsolutePath();
									map.put(folderPath, fileList);
									getFile(folderPath, fileList, folderPath);
									return Thread.currentThread().getName();
								}
							});
						} catch (Exception e) {
							e.printStackTrace();
						}
					} else {
						String mimeType = careFile(file.getName());
						if (!TextUtils.isEmpty(mimeType)) {
							Logd(TAG, path + "==过滤的文件111====" + file.getName());
							String date = simpleDateFormat.format(new Date(file.lastModified()));
							Logd(TAG, "=scanFile,根目录的子文件：" + file.getAbsolutePath());
							if (FILTER) {
								if (file.length() >= MINIMAL_FILE_SIZE) {
									Document document = new Document(file.getAbsolutePath(), file.length(), file.getName(), file.getName(), date, date, mimeType, System.currentTimeMillis());
									d.add(document);
									map.put(root.getAbsolutePath(), d);
								}
							} else {
								Document document = new Document(file.getAbsolutePath(), file.length(), file.getName(), file.getName(), date, date, mimeType, System.currentTimeMillis());
								d.add(document);
								map.put(root.getAbsolutePath(), d);
							}
						} else {
							Logd(TAG, "==scanFile====" + file.getAbsolutePath());
						}
						Logw(TAG, file.getAbsolutePath() + "=scanFile: 路径：" + d.toString());
					}
				}
				Log.i(TAG, map.size() + "====for完成耗时==" + (System.currentTimeMillis() - startTime));
			}
		}
		Log.i(TAG, "一共线程数：====" + threadSum);
		for (int i = 0; i < threadSum; i++) {
			try {
				Future<String> f = completionService.take();
				Log.i(TAG, f.get() + "扫描用了" + (System.currentTimeMillis() - startTime) + "毫秒====" + i);
				initData(i);
			} catch (ExecutionException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		if (service != null) {
			service.shutdown();
			service = null;
			status = IDLE;
			mOnFileScanListener = null;
		}

	}

	static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy 年 MM 月 dd 日");

	private static void getFile(String path, List<Document> fileList, String folderPath) {
		if (!TextUtils.isEmpty(path)) {
			File root = new File(path);
			if (root.isDirectory()) {
				final File[] files = root.listFiles();
				for (File file : files) {
					if (file.isDirectory()) {
						if (TextUtils.isEmpty(ignoreDirectory(file.getName())))
							continue;
						getFile(file.getAbsolutePath(), fileList, folderPath);
					} else {
						String mimeType = careFile(file.getName());
						if (!TextUtils.isEmpty(mimeType)) {
							Log.i(TAG, path + "==过滤的文件111====" + file.getName());
							String date = simpleDateFormat.format(new Date(file.lastModified()));
							if (FILTER) {
								if (file.length() >= MINIMAL_FILE_SIZE) {
									Document document = new Document(file.getAbsolutePath(), file.length(), file.getName(), file.getName(), date, date, mimeType, System.currentTimeMillis());
									fileList.add(document);
								}
							} else {
								Document document = new Document(file.getAbsolutePath(), file.length(), file.getName(), file.getName(), date, date, mimeType, System.currentTimeMillis());
								fileList.add(document);
							}
						} else {
//                            Log.i(TAG, "==忽略====" + file.getName());
						}
					}
				}
			} else {
				String mimeType = careFile(root.getName());
				String date = simpleDateFormat.format(new Date(root.lastModified()));
				if (!TextUtils.isEmpty(mimeType)) {
					Log.i(TAG, path + "==过滤的文件====" + root.getName());
					if (FILTER) {
						if (root.length() >= MINIMAL_FILE_SIZE) {
							Document document = new Document(root.getAbsolutePath(), root.length(), root.getName(), root.getName(), date, date, mimeType, System.currentTimeMillis());
							fileList.add(document);
						}
					} else {
						Document document = new Document(root.getAbsolutePath(), root.length(), root.getName(), root.getName(), date, date, mimeType, System.currentTimeMillis());
						fileList.add(document);
					}
				} else {
//                    Log.i(TAG, "==忽略====" + root.getName());
				}
			}
		}
	}

	/**
	 * 查找的文件后缀
	 *
	 * @param name
	 * @return
	 */
	private static String careFile(String name) {
		String b = "";
		name = name.toLowerCase();
		for (String string : files) {
			if (name.endsWith(string)) {
				b = string.substring(1, string.length());
				return b;
			}
		}
		return b;
	}

	private static void initData(int index) {
		int size = 0;
		Log.e(TAG, size + "==initData111====" + map.size());
		documents.clear();
		for (Map.Entry<String, List<Document>> entry : map.entrySet()) {
			int fileSize = entry.getValue().size();
			if (fileSize < 1) {
				continue;
			}
			Log.w(TAG, entry.getKey() + "==文件夹对应的文件个数====" + fileSize);
			documents.put(entry.getKey(), entry.getValue());
			size += fileSize;
		}
		Log.i(TAG, index + "==" + size + "==initData222====" + map.size());
		if (size >= QUICK_SHOW_NUM) {
			if (first) {
				first = false;
				Log.i(TAG, "================================更新====文件大于10================================" + mOnFileScanListener);
				if (mOnFileScanListener != null) {
					mOnFileScanListener.onSuccess(documents);
				}
			}
		}
		if (index == threadSum - 1) {
			Log.i(TAG, documents.size() + "总的================================更新======全部==============================" + mOnFileScanListener);
			if (mOnFileScanListener != null) {
				mOnFileScanListener.onSuccess(documents);
			}
		}
	}

}
