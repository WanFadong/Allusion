package save;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.mongodb.client.FindIterable;

public class FileHelper {
	/**
	 * 从文件中读取典源
	 * 
	 * @return
	 */
	public List<List<Source>> readAllusionSource() {
		List<List<Source>> sources = new ArrayList<List<Source>>();
		String file = "dianyuan-15.txt";
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			String line = null;
			List<Source> oneSources = new ArrayList<Source>();
			boolean firstLine = true;
			Source source = new Source();
			while ((line = reader.readLine()) != null) {
				// 第一行是题目；第二行是内容；
				// 如果是====则是下一个典故
				if (line.charAt(0) != '=') {
					if (firstLine) {
						source.setTitle(line);
						firstLine = false;
					} else {
						source.setContent(line);
						firstLine = true;
						oneSources.add(source);
						source = new Source();
					}
				} else {
					// 数据纠错
					if (!firstLine) {
						System.out.println("错误数据" + line);
						firstLine = true;
					} else {
						sources.add(oneSources);
						oneSources = new ArrayList<Source>();
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sources;
	}

	/**
	 * 读取典面
	 * 
	 * @return
	 */
	public List<List<String>> readAllusionPattern() {
		List<List<String>> patterns = new ArrayList<List<String>>();
		String file = "dianmian-15.txt";
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			String line = null;
			List<String> onePatterns = null;
			while ((line = reader.readLine()) != null) {
				String space1 = " ";
				String space2 = "　";// 表意空格
				line = line.replaceAll(space2, space1);
				String[] array = line.split(" ");
				if (array.length > 1) {// 去除空行
					onePatterns = new ArrayList<String>();
					for (int i = 1; i < array.length; i++) {// 去掉"典故"二字
						onePatterns.add(array[i]);
					}
					patterns.add(onePatterns);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return patterns;
	}

	/**
	 * 读取用例
	 * 
	 * @return
	 */
	public List<List<Example>> readAllusionExample() {
		int sum = 0;
		int caseSum = 0;
		int lineSum = 0;
		List<List<Example>> examples = new ArrayList<List<Example>>();
		String file = "yongli-15.txt";
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			String line = null;
			List<Example> oneExamples = new ArrayList<Example>();// 一个典故的用例
			while ((line = reader.readLine()) != null) {
				lineSum++;
				// 如果是空行，那么就到了下一个用例；
				// 否则解析；
				String array[] = line.split(" ");
				if (array.length == 3) {
					caseSum++;
					Example example = new Example(array[0], array[1], array[2]);
					oneExamples.add(example);
				} else if (array.length == 2) {
					caseSum++;
					// 没有诗名
					Example example = new Example(array[0], "", array[1]);
					oneExamples.add(example);
				} else if (array.length == 4) {
					caseSum++;
					// 多出“其二”
					Example example = new Example(array[0], array[1] + " " + array[2], array[3]);
					oneExamples.add(example);
				} else {
					sum++;
					if (oneExamples.size() != 0) {
						examples.add(oneExamples);
						oneExamples = new ArrayList<Example>();
					}

				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		// System.out.println(sum);
		// System.out.println(caseSum);
		// System.out.println(lineSum);
		return examples;
	}


	public static void main(String[] args) {
		List<List<Source>> sources = new FileHelper().readAllusionSource();
		System.out.println(sources.size());
		System.out.println(sources.get(1000).get(0).getContent());
	}

	private void testYongLi() {
		List<List<Example>> examples = new FileHelper().readAllusionExample();
		System.out.println(examples.size());
		for (int i = 0; i < examples.size(); i++) {
			List<Example> oneExamples = examples.get(i);
			for (int j = 0; j < oneExamples.size(); j++) {
				Example example = oneExamples.get(j);
				// System.out.print(example.getPoet() + " ");
			}
			// System.out.println();
		}
	}

	private void testdianmian() {
		List<List<String>> patterns = new FileHelper().readAllusionPattern();
		System.out.println(patterns.size());
		for (int i = 1000; i < patterns.size(); i++) {
			List<String> onePatterns = patterns.get(i);
			for (int j = 0; j < onePatterns.size(); j++) {
				String pattern = onePatterns.get(j);
				System.out.print(pattern + " ");
			}
			System.out.println();
		}
	}
}
