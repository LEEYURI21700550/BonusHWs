package edu.handong.csee;



import java.io.File;

import java.util.ArrayList;
import java.util.Arrays;


import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

import org.apache.commons.io.comparator.LastModifiedFileComparator;
import org.apache.commons.io.comparator.NameFileComparator;
import org.apache.commons.io.comparator.SizeFileComparator;


public class Command {
	String path;
	ArrayList<String> fileNames = new ArrayList<String>();

	boolean help;
	boolean rOption; // 역순
	boolean tOption; // 수정순
	boolean mOption; // 가로 출력
	boolean SOption; //크기 순 출력
	boolean QOption; // "" 출력

	public void run(String[] args) {
		Options options = createOptions();


		if(parseOptions(options, args)){
			if (help){
				printHelp(options);

				return;
			}

			path = System.getProperty("user.dir");
			System.out.println(path);

			//int f=0;

			File dir = new File(path);
			for(File file : dir.listFiles()) 
				fileNames.add(file.getName());

			File[] files = dir.listFiles();

			if(tOption) {
			System.out.println("\n----------t Option----------\n"); // 마지막으로 수정된 순으로 출력
			Arrays.sort(files, LastModifiedFileComparator.LASTMODIFIED_REVERSE);
			displayFiles(files);
			}


			if(rOption) {
			System.out.println("\n----------r Option----------\n"); // 역순으로 출력	
			Arrays.sort(files, NameFileComparator.NAME_REVERSE);
			displayFiles(files);
			}

			if(mOption) {
			System.out.println("\n----------m Option----------\n"); // 가로 출력
			displayFiles2(files);
			}

			if(SOption) {
			System.out.println("\n----------S Option----------\n");
			Arrays.sort(files, SizeFileComparator.SIZE_SUMDIR_REVERSE);
			displayFilesWithDirectorySizes(files);
			}
			if(QOption) {
			System.out.println("\n----------Q Option----------\n");
			displayFiles3(files);
			}







		}
	}









	private boolean parseOptions(Options options, String[] args) {
		CommandLineParser parser = new DefaultParser();

		try {

			CommandLine cmd = parser.parse(options, args);

			help = cmd.hasOption("h");
			rOption = cmd.hasOption("r");
			tOption = cmd.hasOption("t");
			mOption = cmd.hasOption("m");
			SOption = cmd.hasOption("S");
			QOption = cmd.hasOption("Q");
		} catch (Exception e) {
			printHelp(options);
			return false;
		}

		return true;
	}




	private Options createOptions() {
		Options options = new Options();


		// add options by using OptionBuilder
		options.addOption(Option.builder("h").longOpt("help")
				.desc("Help")
				.build());



		// add options by using OptionBuilder
		options.addOption(Option.builder("r").longOpt("reverse")
				.desc("reverse order while sortiog")
				.build());

		// add options by using OptionBuilder
		options.addOption(Option.builder("t")
				.desc("sort by modification time, newest first")
				.build());


		// add options by using OptionBuilder
		options.addOption(Option.builder("m")
				.desc("fill width with a comma separated list of entries")
				//.hasArg()
				.argName("width display")
				//.required()
				.build());


		// add options by using OptionBuilder
		options.addOption(Option.builder("S")
				.desc("sort by file size, largest first")
				.build());

		// add options by using OptionBuilder
		options.addOption(Option.builder("Q").longOpt("quote-name")
				.desc("enclose entry names in double quotes")
				.build());

		return options;
	}

	private void printHelp(Options options) {
		// automatically generate the help statement
		HelpFormatter formatter = new HelpFormatter();
		String header = "ls";
		String footer ="";
		formatter.printHelp("ls commands", header, options, footer, true);
	}

	public static void displayFiles(File[] files) {
		for (File file : files) {
			System.out.printf("%-20s", file.getName());
		}
		System.out.println(" ");
	}

	public static void displayFiles2(File[] files) {
		int k = files.length;
		for (int i =0; i < k ; i++) {
			if(i == k-1)
				System.out.printf(files[i].getName() + "\n");
			else {
				System.out.printf(files[i].getName() + ", ");
			}
		}
		System.out.println(" ");
	}

	public static void displayFiles3(File[] files) {
		for (File file : files) 
			System.out.printf("%-20s" , "\""+file.getName()+ "\"");
		System.out.println(" ");
	}

	public static void displayFilesWithDirectorySizes(File[] files) {
		for (File file : files) {
			if (file.isDirectory()) {
				System.out.printf("%-20s" , file.getName());
			} else {
				System.out.printf("%-20s", file.getName());
			}
		}
		System.out.println(" ");

	}



}


