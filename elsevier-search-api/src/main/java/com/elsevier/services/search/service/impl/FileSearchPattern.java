package com.elsevier.services.search.service.impl;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * This Class visits all the files in the given folder path and searches for the words that matches the required words
 * @author satishgummadelli
 *
 */
public class FileSearchPattern extends SimpleFileVisitor<Path> {

	public static final String DOT = ".";
	public static final String COMMA = ",";
	public static final String WORD = "[\\s]+";
	
	private Set<String> searchWords;
	private Set<String> filePaths;
	private Set<String> fileExtensions;
	

	public FileSearchPattern(Set<String> words, Set<String> fileExtensions) {
		this.searchWords = words;
		this.filePaths= new HashSet<>();
		this.fileExtensions = fileExtensions;
	}

	@Override
	public FileVisitResult visitFile(Path path, BasicFileAttributes fileAttr) throws IOException {
		Set<String> wordsToSearch = searchWords.stream().map(w->w.toLowerCase()).collect(Collectors.toSet() );
		if(fileExtensions.stream().anyMatch(fe->path.toString().endsWith(fe))) {
			Files.lines(path).map(line -> line.split(WORD)).flatMap(Arrays::stream).forEach(wordInTheLine->{
				if(!wordsToSearch.isEmpty() ) {
					if (wordsToSearch.stream().anyMatch(wordInTheLine::equals)) {
						wordsToSearch.remove(wordInTheLine.toLowerCase());
					} else if(wordInTheLine.endsWith(COMMA)||wordInTheLine.endsWith(DOT)) {
						String wordWithouExtraSuffix = wordInTheLine.substring(0, wordInTheLine.length()-1);
						if(wordsToSearch.stream().anyMatch(wordWithouExtraSuffix::equals)) {
							wordsToSearch.remove(wordWithouExtraSuffix);
						}
					}
				}
			});
			if(wordsToSearch.isEmpty()) {
				getFilePaths().add(path.toString());
			}
		}
		return FileVisitResult.CONTINUE;
	}

	@Override
	public FileVisitResult preVisitDirectory(Path crunchifyPath, BasicFileAttributes crunchifyFileAttr) {
		return FileVisitResult.CONTINUE;
	}

	public Set<String> getFilePaths() {
		return filePaths;
	}

}
