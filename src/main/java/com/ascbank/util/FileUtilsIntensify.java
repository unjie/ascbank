package com.ascbank.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileUtilsIntensify {

	private static final Logger log = LoggerFactory.getLogger(FileUtilsIntensify.class);

	public static ZipOutputStream getZipOutputStream(File zipFile) throws FileNotFoundException {
		return new ZipOutputStream(new FileOutputStream(zipFile));
	}

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

	public static void uncompress(File zipFile, Path path) throws IOException {

		// String Parent="C:\\Users\\HAN\\Desktop"; //输出路径（文件夹目录）

		File file = null;
		ZipEntry entry;
		BufferedOutputStream bout = null;
		FileOutputStream out = null;
		ZipInputStream zis = null;
		BufferedInputStream bin = null;
		try {
			zis = new ZipInputStream(new FileInputStream(zipFile));
			bin = new BufferedInputStream(zis);
			while ((entry = zis.getNextEntry()) != null && !entry.isDirectory()) {
				file = path.resolve(entry.getName()).toFile();// new File(Parent, );
				if (!file.exists()) {
					(new File(file.getParent())).mkdirs();
				}
				out = new FileOutputStream(file);
				bout = new BufferedOutputStream(out);
				int b;
				while ((b = bin.read()) != -1) {
					bout.write(b);
				}
				bout.close();
				out.close();
				FileUtilsIntensify.log.debug("{} 解压成功", file);
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			FileUtilsIntensify.log.error(" uncompress  :" + e);
			throw e;

		} finally {
			if (bout != null) {
				try {
					bout.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			try {
				if (bin != null) {
					bin.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				if (zis != null) {
					zis.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	/**
	 * 压缩单个文件
	 *
	 * @throws IOException
	 */
	public static ZipOutputStream ZipFile(File file, File zipFile) throws IOException {

		InputStream input = new FileInputStream(file);
		ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(zipFile));
		zipOut.putNextEntry(new ZipEntry(file.getName()));
		int temp = 0;
		while ((temp = input.read()) != -1) {
			zipOut.write(temp);
		}
		input.close();
		// zipOut.close();

		return zipOut;
	}

}
