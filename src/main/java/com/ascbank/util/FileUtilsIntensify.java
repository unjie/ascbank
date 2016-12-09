package com.ascbank.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author jie
 *
 */
public class FileUtilsIntensify extends FileUtils {
	
	private static final Logger log = LoggerFactory.getLogger(FileUtilsIntensify.class);

	/**
	 * 多文件压缩
	 *
	 * @param rootPath
	 *            根路径
	 * @param destination
	 *            相对目标压缩文件路径
	 * @param files
	 *            被压缩文件相对路径
	 * @throws IOException
	 */
	public static void compress(Path rootPath, String destination, String[] files) throws IOException {
		ZipOutputStream zipOut = null;
		try {
			zipOut = FileUtilsIntensify.getZipOutputStream(rootPath.resolve(destination).toFile());
			for (String p : files) {
				File f = rootPath.resolve(p).toFile();
				putFileZip(zipOut, f);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw e;
		} finally {
			if (zipOut != null) {
				zipOut.close();
			}
		}
	}
	
	/**
	 * 复制文件 不需要区分File 或 Directory
	 *
	 * @param srcFile
	 *            源文件
	 * @param destFile
	 *            目标文件
	 * @throws IOException
	 */
	public static void copy(File srcFile, File destFile) throws IOException {
		if (srcFile.isFile()) {
			copyFile(srcFile, destFile);
		} else {
			copyDirectory(srcFile, destFile);
		}
	}
	
	/**
	 * 复制文件
	 *
	 * @param rootPath
	 *            根路径
	 * @param destination
	 *            相对 目标文件夹
	 * @param rename
	 *            单个文件重命名名称
	 * @param files
	 *            被复制文件相对路径
	 * @throws IOException
	 */
	public static void copy(Path rootPath, String destination, String rename, String... files) throws IOException {
		Path dirPath = rootPath.resolve(destination);
		File srcFile = null, destFile = null;
		if (files.length == 1 && rename != null) {
			srcFile = rootPath.resolve(files[0]).toFile();
			destFile = dirPath.resolve(rename).toFile();
			copy(srcFile, destFile);
		} else {
			for (String p : files) {
				// 被复制目标文件
				srcFile = rootPath.resolve(p).toFile();
				// 复制srcFile >> destFile 目标文件
				destFile = dirPath.resolve(srcFile.getName()).toFile();
				copy(srcFile, destFile);
			}
		}

	}

	/**
	 * Copies bytes from an {@link InputStream} <code>source</code> to a file <code>destination</code>. The directories up to <code>destination</code> will be created if they don't already exist. <code>destination</code> will be overwritten if it already exists. 与copyInputStreamToFilex相比 此方法 不会关闭source
	 *
	 * @param source
	 *            the <code>InputStream</code> to copy bytes from, must not be {@code null}
	 * @param destination
	 *            the non-directory <code>File</code> to write bytes to (possibly overwriting), must not be {@code null}
	 * @throws IOException
	 *             if <code>destination</code> is a directory
	 * @throws IOException
	 *             if <code>destination</code> cannot be written
	 * @throws IOException
	 *             if <code>destination</code> needs creating but can't be
	 * @throws IOException
	 *             if an IO error occurs during copying
	 * @since 2.0
	 */
	public static void copyingInputStreamToFile(InputStream source, File destination) throws IOException {
		FileOutputStream output = openOutputStream(destination);
		try {
			IOUtils.copy(source, output);
			output.close(); // don't swallow close Exception if copy completes normally
		} finally {
			IOUtils.closeQuietly(output);
		}
		
	}
	
	/**
	 * @param rootPath
	 *            根路径
	 * @param destination
	 *            被创建目标文件夹相对路径
	 * @return true if and only if the directory was created, along with all necessary parent directories; false otherwise
	 */
	public static Boolean createFolder(Path rootPath, String destination) {
		log.debug("----createFolder ---{}",destination);
		return rootPath.resolve(destination).toFile().mkdirs();
	}
	
	/**
	 * 迭代文件目录
	 *
	 * @param path
	 *            文件路径
	 * @param fileEach
	 *            遍历文件接口,实现 需要each 方法
	 * @throws IOException
	 */
	public static void directoryEach(Path path, FileEach fileEach) throws IOException {
		File file = path.toFile();
		
		log.debug("--{}----------", file.getPath());
		if (file.isDirectory()) {
			log.debug("--{}----------", file.getName());

			for (File f : file.listFiles()) {
				fileEach.each(f);
			}
		}
	}

	/**
	 * 获取文件
	 *
	 * @param rootPath
	 *            根路径
	 * @param destination
	 *            目标文件夹相对路径
	 * @return
	 */
	public static File getFile(Path rootPath, String destination) {
		return rootPath.resolve(destination).toFile();
	}

	/**
	 * 获取到指定文件的压缩字节流对象
	 *
	 * @param zipFile
	 *            将要写入的压缩文件
	 * @return ZipOutputStream 压缩输出字节流
	 * @throws FileNotFoundException
	 */
	public static ZipOutputStream getZipOutputStream(File zipFile) throws FileNotFoundException {
		return new ZipOutputStream(new FileOutputStream(zipFile));
	}

	/**
	 * 文件移动
	 *
	 * @param srcFile
	 *            被移动文件
	 * @param destFile
	 *            目标文件
	 * @throws IOException
	 */
	public static void move(File srcFile, File destFile) throws IOException {
		if (srcFile.isFile()) {
			moveFile(srcFile, destFile);
		} else {
			moveDirectory(srcFile, destFile);
		}
	}

	/**
	 * 文件移动
	 *
	 * @param driveRoot
	 *            根目录
	 * @param destination
	 *            相对目标移动位置
	 * @param files
	 *            被移动文件相对位置
	 * @throws IOException
	 */
	public static void move(Path driveRoot, String destination, String... files) throws IOException {
		// TODO Auto-generated method stub
		Path destPath = driveRoot.resolve(destination);
		File srcFile, destFile;
		for (String p : files) {
			srcFile = driveRoot.resolve(p).toFile();
			destFile = destPath.resolve(srcFile.getName()).toFile();
			move(srcFile, destFile);
		}
	}

	/**
	 * 将文件添加到已有的压缩字节流中
	 *
	 * @param zipOut
	 *            压缩输出字节流对象
	 * @param file
	 *            将要压缩的文件
	 * @return ZipOutputStream 压缩字节流
	 * @throws IOException
	 */
	public static ZipOutputStream putFileZip(ZipOutputStream zipOut, File file) throws IOException {
		InputStream input = new FileInputStream(file);
		zipOut.putNextEntry(new ZipEntry(file.getName()));

		int temp = 0;
		while ((temp = input.read()) != -1) {
			zipOut.write(temp);
		}
		input.close();
		// zipOut.close();
		return zipOut;
	}

	/**
	 * 读取文件到输出流
	 *
	 * @param rootPath
	 *            根路径
	 * @param destination
	 *            目标文件夹相对路径
	 * @param os
	 *            需要写入的输出字节流
	 * @throws IOException
	 */
	public static void readFileToOutputStream(Path rootPath, String destination, OutputStream os) throws IOException {
		File file = rootPath.resolve(destination).toFile();
		FileUtilsIntensify.copyFile(file, os);
	}

	/**
	 * 删除文件
	 *
	 * @param rootPath
	 *            根路径
	 * @param files
	 *            相对文件路径(可以是文件夹)
	 * @return 返回files 删除成功的个数
	 */
	public static int remove(Path rootPath, String... files) {
		File file = null;
		int i = 0;
		log.debug("---------remove 1 {}-------------", rootPath);
		for (String p : files) {
			file = rootPath.resolve(p).toFile();
			log.debug("---------remove 2 {}-------------", p);
			if (file.exists()) {
				log.debug("---------remove 3 {}-------------", p);
				if (FileUtils.deleteQuietly(file)) {
					i++;
				}
			}
			
		}
		return i;
	}

	/**
	 * 解压文件
	 *
	 * @param zipFile
	 *            压缩文件路径
	 * @param path
	 *            解压路径
	 * @throws IOException
	 */
	public static void uncompress(Path zipPath, Path path) throws IOException {
		File file = null,
				zipFile = zipPath.toFile();
		ZipEntry entry;
		ZipInputStream zis = null;
		try {
			zis = new ZipInputStream(new FileInputStream(zipFile));
			FileUtilsIntensify.log.debug("{} 解压", zis);
			while ((entry = zis.getNextEntry()) != null && !entry.isDirectory()) {
				file = path.resolve(entry.getName()).toFile();
				FileUtilsIntensify.log.debug("{} 解压", file);
				if (!file.getParentFile().exists()) {
					file.getParentFile().mkdirs();
				}
				FileUtilsIntensify.log.debug("{} 解压 >>>...", file);
				copyingInputStreamToFile(zis, file);
				FileUtilsIntensify.log.debug("{} 解压成功", file);
			}

		} finally {
			if (zis != null) {
				zis.close();
			}
			
		}

	}
	
	/**
	 * @param rootPath
	 *            根路径
	 * @param zipPath
	 *            压缩文件相对路径
	 * @param path
	 *            解压目标相对路径
	 * @throws IOException
	 */
	public static void uncompress(Path rootPath, String zipPath, String path) throws IOException {
		Path pa = rootPath.resolve(path),
				zip = rootPath.resolve(zipPath);
		uncompress(zip, pa);

	}
	
	/**
	 * 文件数据写入
	 *
	 * @param path
	 *            文件路径
	 * @param data
	 *            写入的数据
	 * @throws IOException
	 */
	public static void writeStringToFile(Path path, String data) throws IOException {
		writeStringToFile(path.toFile(), data);
	}

	/**
	 * 压缩单个文件
	 *
	 * @param file
	 *            被压缩文件
	 * @param zipFile
	 *            压缩目标文件
	 * @return ZipOutputStream 压缩字节流
	 * @throws IOException
	 */
	public static ZipOutputStream zipFile(File file, File zipFile) throws IOException {
		
		// InputStream input = new FileInputStream(file);
		ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(zipFile));
		zipOut.putNextEntry(new ZipEntry(file.getName()));

		copyFile(file, zipOut);
		/*
		 * int temp = 0; while ((temp = input.read()) != -1) { zipOut.write(temp); } input.close();
		 */
		// zipOut.close();

		return zipOut;
	}

	/**
	 * @param rootPath
	 *            根路径
	 * @param destination
	 *            目标文件夹相对路径
	 * @param files
	 *            被压缩文件相对路径
	 * @return 压缩文件路径 Path 对象
	 * @throws IOException
	 */
	public static Path zipFiles(Path rootPath, String destination, String... files) throws IOException {
		
		ZipOutputStream zipOut = null;
		Path zipPath = rootPath.resolve(destination);
		try {
			zipOut = getZipOutputStream(zipPath.toFile());
			for (String p : files) {
				putFileZip(zipOut, rootPath.resolve(p).toFile());
			}
		} finally {
			if (zipOut != null) {
				zipOut.close();
			}
		}

		return zipPath;
	}

	public FileUtilsIntensify() {
		super();
		// TODO Auto-generated constructor stub
	}
	


}
