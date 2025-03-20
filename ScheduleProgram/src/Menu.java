
public class Menu {

	public static void mainMenu() {
		System.out.println("-----------------------*********일정 관리 프로그램*********------------------------");
		System.out.println("1. 일정 추가");
		System.out.println("2. 일정 검색");
		System.out.println("3. 일정 삭제");
		System.out.println("4. 일정 수정");
		System.out.println("5. 전체 일정 보여주기");
		System.out.println("6. 전체 일정 화일에 저장하기");
		System.out.println("7. 프로그램 종료");
		System.out.println("---------------------------------------------------------------------------");
		System.out.print("원하시는 프로그램 번호를 입력해주세요: ");
	}
	
	public static void searchMenu() {
		System.out.println();
		System.out.println("-----------------------*********검색 방법*********------------------------");
		System.out.println("1. 특정 시기 후의 일정 검색");
		System.out.println("2. 기간 사이 일정 검색");
		System.out.println("3. 키워드로 검색");
		System.out.println("4. 우선순위로 검색");
		System.out.println("5. 카테고리로 검색");
		System.out.println("6. 이전 메뉴(메인 메뉴)로 돌아가기");
		System.out.println("---------------------------------------------------------------------------");
		System.out.print("원하시는 검색 방법 번호를 입력해주세요 : ");
		
	}
	
	public static void categoryMenu() {
		System.out.println();
		System.out.println("-----------------------*********일정 카테고리*********------------------------");
		System.out.println("1. 공부[s]");
		System.out.println("2. 가족[f]");
		System.out.println("3. 학교[w]");
		System.out.println("4. 개인[p]");

		System.out.println("---------------------------------------------------------------------------");
		System.out.print("일정의 카테고리에 해당하는 문자를 입력해주세요 : ");
	}

}
