package AlgoProject.Project3;

public class test {

	public static void main(String args[]) {
		int[] a = { 2, 1, 5, 8, 1, 9 };
		int i = 0, j = 0;
		for (;;) {
			while (a[++i] - 3 < 0) {
				System.out.println(i);
			}
			while (a[++j] - 3 > 0) {
				System.out.println(j);
			}
		}
	}
}
