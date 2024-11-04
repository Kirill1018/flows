package threads;
import java.util.function.Predicate;
import java.util.function.Consumer;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
public class Threads {
	static Scanner scanner = new Scanner(System.in);
	static String student = new String();
	public static void main(String[] args) {
		FirstObj firstObj = new FirstObj("первый объект");
		Converter<FirstObj, SecObj> converter = x -> new SecObj(x.name);
		SecObj secObj = converter.convert(firstObj);
		System.out.println("сейчас второй объект поменял расположение, теперь на его месте стоит " + secObj.sake);
		Predicate<Integer> predicate = x -> x % 2 == 0, failure = x -> x < 7;
		System.out.println(predicate.test(2));
		System.out.println(predicate.test(1));
		System.out.println("оценка студента");
		int mark = scanner.nextInt();
		if (mark < 1) {
			System.out.println("минимальная оценка - 1");
			return;
		}
		if (mark > 12) {
			System.out.println("максимальная оценка - 12");
			return;
		}
		predicate = x -> x > 6;
		String status = new String();
		if (predicate.test(mark)) status = "студент прошёл экзамен";
		else if (failure.test(mark)) status = "студент отправляется на пересдачу";
		System.out.println(status);
		try {
			FileOutputStream stream = new FileOutputStream("студент.txt");
			Consumer<String> consumer = x -> {
				try {
					stream.write(x.getBytes());
					stream.flush();
					stream.close();
				}
				catch(IOException ioExc) { ioExc.printStackTrace(); }
			};
			consumer.accept(status);
		}
		catch(FileNotFoundException exception) { exception.printStackTrace(); }
	}
}
interface Converter<T, N> { N convert(T t); }