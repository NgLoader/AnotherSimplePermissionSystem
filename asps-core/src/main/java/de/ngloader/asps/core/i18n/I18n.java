package de.ngloader.asps.core.i18n;

import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class I18n {

	public static final String TRANSLATION_NOT_FOUND = "§cUnable to find the following translation§8! §7(§e%s§7)";
	public static final I18nLocale DUMMY_I18N = new I18nLocale(Locale.US);

	private static Path I18N_FOLDER;

	private static final Gson GSON = new GsonBuilder().create();

	private static final CacheLoader<Locale, I18nLocale> CACHE_LOADER = new CacheLoader<>() {

		@Override
		public I18nLocale load(Locale key) throws Exception {
			I18nLocale i18n = null;

			Path filename = I18N_FOLDER.resolve(String.format("%s.json", key.toLanguageTag()));
			try (FileReader fileReader = new FileReader(filename.toFile())) {
				 i18n = GSON.fromJson(fileReader, I18nLocale.class);
			} catch (Exception e) {
				e.printStackTrace();
			}

			return i18n != null ? i18n : DUMMY_I18N;
		}
	};

	private static final LoadingCache<Locale, I18nLocale> LOCALE_CACHE = CacheBuilder.newBuilder()
			.expireAfterAccess(10, TimeUnit.MINUTES)
			.build(CACHE_LOADER);

	public static void updateLocaleFiles(Path folder) throws IOException {
		if (Files.isDirectory(folder)) {
		} else if (Files.notExists(folder)) {
			Files.createDirectories(folder);
		} else {
			throw new FileAlreadyExistsException(folder.toString(), null, "Folder can not be a file!");
		}

		I18N_FOLDER = folder;

		try {
			Path systemFilePath = Path.of(I18nLocale.class.getClassLoader().getResource("translation/").toURI());
			Files.walkFileTree(systemFilePath, new FileVisitor<Path>() {

				@Override
				public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
					if (file.getFileName().toString().matches("[a-z][a-z]_[A-Z][A-Z].json")) {
						Path outputFile = I18N_FOLDER.resolve(file.getFileName().toString());
						try (OutputStream outputStream = Files.newOutputStream(outputFile, StandardOpenOption.CREATE_NEW)) {
							Files.copy(file, outputStream);
						}
					}
					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
					return FileVisitResult.TERMINATE;
				}

				@Override
				public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
					return FileVisitResult.CONTINUE;
				}
			});
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}

	public static I18nLocale get(Locale locale) {
		try {
			return LOCALE_CACHE.get(locale);
		} catch (Exception e) {
			e.printStackTrace();
			return DUMMY_I18N;
		}
	}
}