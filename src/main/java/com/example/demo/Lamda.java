//package com.example.demo;
//
//import java.io.BufferedReader;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.util.HashSet;
//import java.util.Iterator;
//import java.util.Set;
//import java.util.stream.Stream;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import com.example.demo.repository.UserRepository;
//
//@Controller
//@RequestMapping("")
//public class Lamda {
//
//	@Autowired
//	private UserRepository repository;
//
////	@RequestMapping("/")
////	public void lamda() {
//
////		List<User> userList=repository.findAll();
////	    stream課題1
////		Map<String, String> map = userList.stream().collect(Collectors.toMap(User::getUuid , s -> s.getName()));
////		System.out.println(map.get("99caed05-15a4-4c4a-a29d-20d52e3899cd"));
//
////      stream課題2
////		userList.stream().filter(u -> u.getAuthority()==1).forEach(u -> System.out.println(u));
//
////      stream課題3 配列に指定の文字があるか　anyMatchは何か入っていればtrueになる
////		String[] str = {"aaa" , "bbbb","ccc"};
////		List<String> list= Arrays.asList(str);
////		boolean match=list.stream().anyMatch(r -> r.contains("1.8") || r.contains("JDK") || r.contains("JDK1.8"));
////		System.out.println(match);
//
////      stream課題4 素数を求める　要素の有無はanyMatch ,allMatch ,noneMatchでいける。anyMatch(n -> true);は要素があるかどうか
////		int num=0;
////		boolean sosu =IntStream.range(2, num).filter(n -> num%n==0).anyMatch(n -> true);
////		System.out.println(sosu);
//
//		BufferedReader reader = null;
//
//		try {
//			reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File("/Users/namikitsubasa/desktop/mercari.txt")), "UTF-8"));
////			String line;
//			Set<String> ipAddresses = new HashSet<>();
////			while ((line = reader.readLine()) != null) {
////				String[] words = line.split(" ");
////				ipAddresses.add(words[0]);
////			}
//
//			reader.lines().map(l -> l.split(" ")).forEach(l -> ipAddresses.add(l[0]));
//			
////			for (String ipAddress : ipAddresses) {
////				System.out.println(ipAddress);
////			}
//			ipAddresses.forEach(i -> System.out.println(i));
//		} catch (IOException ex) {
//			// ファイル読み込み時のエラー処理
//		} finally {
//			if (reader != null) {
//				try {
//					reader.close();
//				} catch (IOException ex) {
//					// ignored
//				}
//			}
//		}
//	}
//}
