import java.io.*;
import java.util.*;

public class User {
	//인터페이스 윗단에 입출력
	static User user = new User();
	Scanner scan = new Scanner(System.in);
	Manager manager = new Manager(100);
	Schedule s = new Schedule();
	
	int startYear = 2024, startMonth = 0, startDay = 1,
            startHour = 12, startMinute = 0,
            endYear = 2024, endMonth = 0, endDay = 1,
            endHour = 12, endMinute = 0;
	String lable = null, memo = null, keyword = null;
	char category = ' ';
	int importance = 1;
	GregorianCalendar startDateTime = new GregorianCalendar();
	GregorianCalendar endDateTime = new GregorianCalendar();
	
	int indexOrder = 0;
	int mainMenu = 0, subMenu = 0;
	String filename = "test.txt";
	
	public void showSchedule(Schedule s) { // 단일 일정을 출력 함수
		System.out.println(s.showIsCompleted()+"일정"+s.getSerialNumber()+": " +
				"["+s.getCategory()+"]"+ s.getLabel() +s.showImportance());
		System.out.println("시작: " + s.getStart().get(Calendar.YEAR) + "년 " + 
				(s.getStart().get(Calendar.MONTH) + 1) + "월 " + s.getStart().get(Calendar.DAY_OF_MONTH) + "일 " + 
				s.getStart().get(Calendar.HOUR_OF_DAY)+"시 " +s.getStart().get(Calendar.MINUTE) +"분 " );
		System.out.println("종료: " + s.getEnd().get(Calendar.YEAR) + "년 " + 
				(s.getEnd().get(Calendar.MONTH) + 1 ) + "월 " + s.getEnd().get(Calendar.DAY_OF_MONTH) + "일 " + 
				s.getEnd().get(Calendar.HOUR_OF_DAY)+"시 " +s.getEnd().get(Calendar.MINUTE) +"분 " );
		System.out.println("메모: " + s.getMemo());
		System.out.println();
	}
	
	public void showSchedule(int[] indices) { //인덱스 배열에 해당하는 일정을 출력 함수
		System.out.println();
		
		if(user.manager.getIndicesSize() == 0) {
			System.out.println("검색 결과가 없습니다.");
			System.out.println("이전 메뉴(메인 메뉴)로 돌아갑니다.");
		}
		else {
			System.out.println("-----------------------*********일정 검색 결과*********------------------------");
			System.out.println("총 " + user.manager.getIndicesSize() + "개의 검색 결과가 있습니다.");
			System.out.println();
			
			Schedule[] schedules = manager.getSchedules();
			
			for(int i = 0; i < user.manager.getIndicesSize(); i++) {
				System.out.println( "●" + (i+1) + "번째 검색 결과: ");
				user.showSchedule(schedules[indices[i]]);
			}
			System.out.println("---------------------------------------------------------------------------");
		}
	}
	
	public void inputLable() {
		while(true) {
			try {
				System.out.println();
				scan.nextLine(); //버퍼 비움
				System.out.print("일정 이름: ");
				String input = scan.nextLine();
				
				if(input.isBlank()) {
					//일정을 추가할 때, 일정 이름 항목에 공백을 입력한 경우에 대한 예외처리
					if(mainMenu == 1) { //추가
						throw new Exception("일정 이름을 공백으로 추가할 수 없습니다.");
					}
					else if (mainMenu == 4) { //수정
						int index = manager.getIndices()[indexOrder-1];
						lable = manager.getSchedules()[index].getLabel();
					}
				} 
				else {
					lable = input;
				}
				
				break;
			
			} catch (Exception e) {
				System.out.println(e.getMessage() + "일정 이름을 다시 입력하세요");
			}
		}
	}
	
	public void inputCategory() {
		while(true) {
			try {
				Menu.categoryMenu();
				String input = scan.nextLine();
				
				if(input.isBlank()) {
					if(mainMenu != 4) {
						throw new Exception("일정을 추가/검색하는 경우에는 카테고리를 공백으로 입력할 수 없습니다. ");
					}
					else if(mainMenu == 4) {
						int index = manager.getIndices()[indexOrder-1];
						category = manager.getSchedules()[index].getCategory();
					}
				}
				else {
					category = input.charAt(0);
					
					//카테고리 입력 유효성 검사하고 예외처리
					boolean isValidInput = false;
					for(char element: s.getCategories()) {
						if(category == element) {
							isValidInput = true;
							break;
						}
					}
					if(isValidInput == false) {
						throw new Exception("유효하지 않은 카테고리를 입력할 수 없습니다. ");
					}
				}
					
				break;
				
			} catch (InputMismatchException e) {
				System.out.println(e.getMessage() + "일정 카테고리를 다시 입력하세요");
			} catch (Exception e) {
				System.out.println(e.getMessage() + "일정 카테고리를 다시 입력하세요");
			}
		}
	
	}
	
	public void inputStartDay() { //시작 년월일
		System.out.println();
		while(true) {
			try {
//				scan.nextLine(); //버퍼 비움
				System.out.println("시작 일시에 대한 정보를 입력해주세요.");
				System.out.print("년도(yyyy): ");
				String input = scan.nextLine();
				
				int index = manager.getIndices()[indexOrder-1];
				if(input.isBlank()) {
					if(mainMenu == 4) { //수정인 경우
						startYear = manager.getSchedules()[index]
								.getStart().get(Calendar.YEAR);
					}
					else { //수정(4)이 아닌 추가(1)/검색(2)할 때, 시작년도 항목에 공백을 입력한 경우에 대한 예외 처리
						throw new Exception("시작 년도를 공백으로 입력할 수 없습니다.");
					}
				}
				else {
					startYear = endYear = Integer.parseInt(input);
					if(startYear < 1970) {
						throw new Exception("시작 년도는 1970보다 큰 수로 입력해야합니다.");
					}
				}
				user.startDateTime.set(Calendar.YEAR, startYear);
				
				System.out.print("월(MM): ");
				input = scan.nextLine();
				
				if(input.isBlank()) { //시작월 항목 공백 입력한 경우
					
					//추가(1)할 때, 시작월 항목에 공백을 입력한 경우에 대한 예외 처리
					if(mainMenu == 1) {
						throw new Exception("일정을 추가하는 경우에는 월을 공백으로 입력할 수 없습니다.");
					}
					else if (mainMenu == 4) {//수정
						startMonth = manager.getSchedules()[index]
								.getStart().get(Calendar.MONTH);
					}
					
				}
				else {//시작월 항목 공백 입력이 아닌 경우
					startMonth = endMonth =  Integer.parseInt(input) - 1;
					if(startMonth < 1 || startMonth > 12) {
						throw new Exception("1에서 12 사이의 숫자를 입력해야 합니다.");
					}
				}
				user.startDateTime.set(Calendar.MONTH, startMonth);
				
				System.out.print("일(dd): ");
				input = scan.nextLine();
				if(input.isBlank()) { //시작일 항목 공백 입력한 경우
					
					//추가(1)할 때, 시작일 항목에 공백을 입력한 경우에 대한 예외 처리
					if(mainMenu == 1) {
						throw new Exception("일정을 추가하는 경우에는 일을 공백으로 입력할 수 없습니다.");
					}
					else if (mainMenu == 4) {//수정
						startDay = manager.getSchedules()[index]
								.getStart().get(Calendar.DAY_OF_MONTH);
					}
					
				}
				else {//시작일 항목 공백 입력이 아닌 경우
					startDay = endDay = Integer.parseInt(input);
					int maxDay = startDateTime.getActualMaximum(Calendar.DAY_OF_MONTH);
					
					if(startDay < 1 || startDay > maxDay) {
						throw new Exception("일에 대한 입력은 1에서" + maxDay +" 사이의 숫자를 입력해야 합니다.");
					}
				}
				user.startDateTime.set(Calendar.DAY_OF_MONTH, startDay);
				
				break;
			} catch(NumberFormatException  e) {
				System.out.println("숫자만 입력해야 합니다. 시작 일시를 처음부터 다시 입력하세요");
			} catch(Exception e) {
				System.out.println(e.getMessage() + " 시작 일시를 처음부터 다시 입력하세요");
			}
		}
	}
	
	public void inputEndDay() { //종료 년월일
		while(true) {
			try {
				System.out.println();
				System.out.println("종료 일시에 대한 정보를 입력해주세요.");
				
				System.out.print("년도(yyyy): ");
				String input = scan.nextLine();
				
				int index = manager.getIndices()[indexOrder-1];
				//추가(1)할 때, 종료년도 항목에 공백을 입력한 경우에 대한 예외 처리
				if(input.isBlank()) {
					if(mainMenu == 1) { 
						throw new Exception("일정을 추가하는 경우에는 종료 년도를 공백으로 입력할 수 없습니다.");
					}
					else if (mainMenu == 4) {//수정
						endYear = manager.getSchedules()[index]
								.getEnd().get(Calendar.YEAR);
					}
				}
				else {
					endYear = Integer.parseInt(input);
					if(endYear < startYear) {
						throw new Exception("시작일은 종료일보다 클 수 없습니다." 
								+ "년 입력 오류: " + startYear + "보다 큰 수로 입력해야합니다.");
					}
				}
				user.endDateTime.set(Calendar.YEAR, endYear);
				
				System.out.print("월(MM): ");
				input = scan.nextLine();
				
				if(input.isBlank()) { //종료월 항목에 공백 입력한 경우
					//추가(1)할 때, 종료월 항목에 공백을 입력한 경우에 대한 예외 처리
					if(mainMenu == 1) {
						throw new Exception("일정을 추가하는 경우에는 월을 공백으로 입력할 수 없습니다.");
					}
					else if (mainMenu == 4) {//수정
						endMonth = manager.getSchedules()[index]
								.getEnd().get(Calendar.MONTH);
					}
				}
				else { //종료월 항목이 공백 입력이 아닌 경우
					endMonth = Integer.parseInt(input) - 1;
					
					if(endMonth < 1 || endMonth > 12) {
						throw new Exception("1에서 12 사이의 숫자를 입력해야 합니다.");
					}
					else if(endYear == startYear && endMonth < startMonth) {
						throw new Exception("시작일은 종료일보다 클 수 없습니다." 
								+ "월 입력 오류: " + (startMonth-1) + "보다 큰 수로 입력해야합니다.");
					}
				}
				user.endDateTime.set(Calendar.MONTH, endMonth);
				
				System.out.print("일(dd): ");
				input = scan.nextLine();
				
				if(input.isBlank()) { //종료일 항목 공백 입력한 경우
					
					//추가(1)할 때, 종료일 항목에 공백을 입력한 경우에 대한 예외 처리
					if(mainMenu == 1) {
						throw new Exception("일정을 추가하는 경우에는 일을 공백으로 입력할 수 없습니다. ");
					}
					else if (mainMenu == 4) {//수정
						endDay = manager.getSchedules()[index]
								.getEnd().get(Calendar.DAY_OF_MONTH);
					}
				}
				else {
					endDay = Integer.parseInt(input);
					int maxDay = endDateTime.getActualMaximum(Calendar.DAY_OF_MONTH);
					if(endDay < 1 || endDay > maxDay) {
						throw new Exception("일에 대한 입력은 1에서" + maxDay +" 사이의 숫자를 입력해야 합니다. ");
					}
					
					if(endYear == startYear && endMonth == startMonth && endDay < startDay) {
							throw new Exception("시작일은 종료일보다 클 수 없습니다." 
									+ "일 입력 오류: " + startDay + "보다 큰 수로 입력해야합니다.");
					}
				}
				
				user.endDateTime.set(Calendar.DAY_OF_MONTH, endDay);
				break;
			} catch(NumberFormatException  e) {
				System.out.println("숫자만 입력해야 합니다. 종료 일시를 처음부터 다시 입력하세요");
			} catch(Exception e) {
				System.out.println(e.getMessage() + " 종료 일시를 처음부터 다시 입력하세요");
			}
		}
	}

	public void inputStartTime() { //시작 시간
		while(true) {
			try {
				System.out.print("시간(HH): ");
				String input = scan.nextLine();
				
				int index = manager.getIndices()[indexOrder-1];
				
				if(!input.isBlank()) {
					startHour = endHour = Integer.parseInt(input);
					
					if(startHour < 1 || startHour > 24) {
						throw new Exception("1에서 24 사이의 숫자를 입력해야 합니다.");
					}
				} else {
					if(mainMenu == 4) {// 수정: 공백으로 시간 설정
						startHour = endHour = manager.getSchedules()[index]
								.getStart().get(Calendar.HOUR);
					}
				}
				
				user.startDateTime.set(Calendar.HOUR_OF_DAY, startHour);
				user.endDateTime.set(Calendar.HOUR_OF_DAY, endHour);
				
				System.out.print("분(mm): ");
				input = scan.nextLine();
				if(!input.isBlank()) {
					startMinute = endMinute = Integer.parseInt(input);
					
					if(startMinute < 0 || startMinute > 60) {
						throw new Exception("1에서 60 사이의 숫자를 입력해야 합니다.");
					}
				} else { //공백 입력한 경우
					if ( mainMenu == 4) {//수정: 공백으로 분 설정
						startMinute = endMinute = manager.getSchedules()[index]
								.getStart().get(Calendar.MINUTE);
					}
				}
				startDateTime.set(Calendar.MINUTE, startMinute);
				endDateTime.set(Calendar.MINUTE, endMinute);
				
				break;
			} catch(NumberFormatException  e) {
				System.out.println("숫자만 입력해야 합니다. 시작 시간을 처음부터 다시 입력하세요");
			} catch (Exception e) {
				System.out.println(e.getMessage() + " 시작 시간을 처음부터 다시 입력하세요");
			}
		}
	}
	
	public void inputEndTime() { //종료 시간
		while(true) {
			try {
				System.out.print("시간(HH): ");
				String input = scan.nextLine();
				
				int index = manager.getIndices()[indexOrder-1];
				
				if(!input.isBlank()) {
					endHour = Integer.parseInt(input);
					
					if(endHour < 1 || endHour > 24) {
						throw new Exception("1에서 24 사이의 숫자를 입력해야 합니다.");
					}
					
					user.endDateTime.set(Calendar.HOUR_OF_DAY, endHour); //수정?
					
					if(startDateTime.compareTo(endDateTime) > 0) {
						throw new Exception("시작일은 종료일보다 클 수 없습니다." 
								+ "시 입력 오류: " + startHour + "보다 큰 수로 입력해야합니다.");
					}
				} 
				else { //공백 입력
					if (mainMenu == 4) {//추가/ 수정: 공백으로 시간 설정
						endHour = manager.getSchedules()[index]
								.getEnd().get(Calendar.HOUR);
					}
				}

				user.endDateTime.set(Calendar.HOUR_OF_DAY, endHour);
				
				System.out.print("분(mm): ");
				input = scan.nextLine();
				
				if(!input.isBlank()) {
					endMinute = Integer.parseInt(input);
					
					if(endMinute < 0 || endMinute > 60) {
						throw new Exception("1에서 60 사이의 숫자를 입력해야 합니다.");
					}
					
					user.endDateTime.set(Calendar.MINUTE, endMinute); //수정?
					
					if(startDateTime.compareTo(endDateTime) > 0) {
						throw new Exception("시작일은 종료일보다 클 수 없습니다." 
								+ "분 입력 오류: " + startMinute + "보다 큰 수로 입력해야합니다.");
					}
					
				}
				else { //공백 입력
					if (mainMenu == 4) {//수정: 공백으로 분 설정
						endMinute = manager.getSchedules()[index]
								.getEnd().get(Calendar.MINUTE);
					}
				}
				
				user.endDateTime.set(Calendar.MINUTE, endMinute);
				
				break;
				
			} catch(NumberFormatException  e) {
				System.out.println("숫자만 입력해야 합니다. 종료 시간을 처음부터 다시 입력하세요");
			} catch(Exception e) {
				System.out.println(e.getMessage() + " 종료 시간을 처음부터 다시 입력하세요");
			}
		}
	}
	
	public void inputMemo() {
		System.out.println();
		System.out.print("메모: ");
        String input = scan.nextLine();
        
        if(input.isBlank()) {
        	if(mainMenu == 1) { //메모 공백으로 일정 추가
        		memo = "";
        	}
        	else if(mainMenu == 4) {//공백으로 수정
        		int index = manager.getIndices()[indexOrder-1];
        		memo = manager.getSchedules()[index].getMemo();
        	}
        }
        else {
        	memo = input;
        }
	}
	
	public void inputImportance() {
		while (true) {
			try {
				System.out.println();
		    	System.out.print("중요도(1~5):" );
		    	String input = scan.nextLine();
		    	
		    	if(!input.isBlank()) { //공백이 아닌 경우
		    		importance = Integer.parseInt(input);
		    		
			        if(importance < 1 || importance > 5) {
			        	throw new Exception("1에서 5 사이의 숫자를 입력해야 합니다.");
			        }
		    	}
		    	else { //공백인 경우
		    		if(mainMenu == 4) {
		    			int index = manager.getIndices()[indexOrder-1];
		    			importance = manager.getSchedules()[index].getImportance();
		    		}
		    	}
		    	
		    	break;
		    	
			} catch(NumberFormatException  e) {
				System.out.println("숫자만 입력해야 합니다. 중요도를 처음부터 다시 입력하세요");
			} catch(Exception e) {
				System.out.println(e.getMessage() + " 중요도를 처음부터 다시 입력하세요");
			}
		}
	}

	public int inputMenu () throws Exception {
		int menu = 0;
		
		while(true) { //메뉴 번호 입력
			try{
				menu = user.scan.nextInt();
				
				//주어진 메뉴 범위 밖의 번호를 입력하면 예외처리
				if(menu < 0 || menu > 6) {
					throw new Exception("메뉴 입력 오류: 1보다 크고 7보다 작은 메뉴를 출력하세요.");
				}
				
				break;
			} catch (InputMismatchException e) {
				user.scan = new Scanner(System.in);
				System.out.println("잘못된 입력입니다. 다시 입력하세요.");
			}
		}
		
		return menu;
	}
	
	public void read() {
		File file = new File(filename);
		
		try {
			if(file.exists()) {
				ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename));
				user.manager.readScheudules(in);
				System.out.println("데이터를 불러왔습니다.");
			} 
			else {
				System.out.println("테스트: 없음");
			}
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	//main 함수
	public static void main(String args[]) throws Exception{
		
		user.read();
		
		while(true) //메인 메뉴
		{
			Menu.mainMenu(); 
			user.mainMenu = user.inputMenu();
			
			switch(user.mainMenu) // 메인 메뉴 
			{
			case 1: //추가
				System.out.println("일정 정보를 입력하세요.(이름, 분류, 시작, 끝, 메모, 중요도)");
				user.inputLable(); 
				user.inputCategory();
				user.inputStartDay();
				user.inputStartTime();
				user.inputEndDay();
				user.inputEndTime();
				user.inputMemo();
		        user.inputImportance();
				
				user.s = new Schedule(user.lable, user.category, user.startDateTime, 
						user.endDateTime, user.memo, user.importance);
				
				//일정 객체 중복 검사
				boolean doesAdd = true;
				if(user.manager.duplicateCheck(user.s)) {
					while(true) { //중복된 일정 추가 while
						try {
							System.out.print("같은 내용의 일정이 존재합니다. 그래도 추가하시겠습니까?(y/n): ");
							String input = user.scan.nextLine();
							
							if(input == null) {
								throw new Exception("공백으로 입력할 수 없습니다.");
							}
							else {
								char answer = input.charAt(0);
								
								if(answer == 'y' || answer == 'Y') {
									System.out.println("같은 이름의 일정을 추가를 마저 진행합니다.");
									
								} else if (answer == 'n' || answer == 'N') {
									System.out.println("같은 이름의 일정은 추가하지 않습니다.");
									doesAdd = false;
									
								} else {
									throw new Exception();
								}
							}
							
							break; //중복된 일정 추가 while 탈출
						} catch(Exception e){
							System.out.println(e.getMessage() + "Y(y) 또는 N(n)로 다시 입력하세요. ");
						}
					}
				}
				
				if(doesAdd) {
					//일정 저장
					user.manager.add(user.s);
					System.out.println("\n일정이 다음과 같이 저장되었습니다.");
					user.showSchedule(user.s);
				}
				
				break; //case1 탈출
				
			case 2:// 일정 검색
			case 3:// 일정 삭제
			case 4:// 일정 수정
				
				System.out.println();
				if(user.mainMenu == 3) {
					System.out.println("일정 삭제를 위한 일정 검색을 하겠습니다.");
				}
				else if(user.mainMenu == 4) {
					System.out.println("일정 수정를 위한 일정 검색을 하겠습니다.");
				}
				
				Menu.searchMenu();
				user.subMenu = user.inputMenu();
				
				int[] indices = new int[user.manager.getSchedulesSize()];
				
				switch(user.subMenu) {
				case 1: //특정 기간 이후 검색
					user.inputStartDay();
					
					//검색
					indices = user.manager.search(user.startDateTime); 
			
					break; //case 1 탈출
				
				case 2: //날짜로 검색하는 경우
					user.inputStartDay(); //시작 일자 입력
					user.inputEndDay(); //끝 일자 입력
					
					//검색
					indices = user.manager.search(user.startDateTime, user.endDateTime); 
					
					break; //case 2 탈출
					
				case 3: //키워드로 검색하는 경우
					while(true) { //키워드 입력 while
						try {
							user.scan.nextLine(); //버퍼 비움
							System.out.print("검색할 키워드를 입력하세요: ");
					    	String keyword = user.scan.nextLine();
					    	
					    	if(keyword.isBlank()) {
					    		throw new Exception ("키워드를 입력하지 않았습니다.");
					    	}
					    	
					    	//일정 검색
							indices = user.manager.search(keyword); 
							
							break; //키워드 입력 while 탈출
							
						} catch (Exception e) {
							System.out.println(e.getMessage() + " 다시 입력하세요.");
						}
					}
					
					break; //case 3 탈출
					
				case 4: //우선순위
					//입력
					user.inputImportance();

			    	//검색
					indices = user.manager.search(user.importance); 
					
					break; //case 4 탈출
				
				case 5: //카테고리
					//입력
					user.inputCategory();
					
			    	//검색
			    	indices = user.manager.search(user.category); 
			    	
					break; //case 5 탈출
					
				case 6://종료
					// 프로그램 종료 안내 출력
					System.out.println("이전 메뉴(메인 메뉴)로 돌아갑니다.");
					break;
					
				}
				
				if(user.subMenu == 6 ) { //이전 단계
					break;
				}
				
				//검색 결과 출력
				user.showSchedule(indices);
				
				if(user.manager.getIndicesSize() == 0) {
					break;
				}
				
				if( user.mainMenu == 3) { //case 3: (단일) 삭제
					System.out.print("몇 번째 일정을 삭제하겠습니까?: ");
					user.indexOrder = user.scan.nextInt();
					//예외 필?
					int index = indices[user.indexOrder-1];
					user.manager.delete(index);
					System.out.println("일정을 삭제하였습니다.");
				}
				else if( user.mainMenu == 4) { //case 4: 수정
					System.out.print("몇 번째 일정을 수정하겠습니까?: ");
					user.indexOrder = user.scan.nextInt();
					int index = indices[user.indexOrder-1];
					
					//정보 입력
					System.out.println();
					System.out.println("해당 일정 정보를 수정하겠습니다.\n"
							+ "(수정을 원하는 항목이 나오면 수정하려는 정보를 입력하고, "
							+ "수정을 원하지 않는 항목이 나오면 엔터를 누르세요.)");
					user.inputLable(); 
					user.inputCategory();
					user.inputStartDay();
					user.inputStartTime();
					user.inputEndDay();
					user.inputEndTime();
					user.inputMemo();
			        user.inputImportance();
			        
					user.s = new Schedule(user.lable, user.category, 
							user.startDateTime, user.endDateTime, user.memo, user.importance);
					
					//일정 수정
					user.manager.edit(index, user.s);
					
					//확인 및 출력
					System.out.println("\n일정을 아래와 같이 수정하였습니다.");
					user.showSchedule(user.s);
					
				}
				
				System.out.println("이전 메뉴(메인 메뉴)로 돌아갑니다.");
				break; //case 2,3,4 탈출
				
			case 5: //모든 일정 출력
				System.out.println("총 " + user.manager.getSchedulesSize() + "개의 일정이 있습니다.");
				for(int i = 0; i < user.manager.getSchedulesSize(); i++) {
					System.out.println();
					System.out.println( "●" + (i+1) + "번째 검색 결과: ");
					user.showSchedule(user.manager.getSchedules()[i]);
				}
			
				break;
				
			case 6: //파일에 전체 일정 저장
				ObjectOutputStream out = null;
				out = new ObjectOutputStream( new FileOutputStream(user.filename));
				user.manager.writeSchedules(out);
				
				System.out.println("전체 일정들을 저장했습니다.");
				break;
				
			case 7: //종료
				System.out.println("프로그램을 종료합니다.");
				break;
				
			default: // 숫자를 잘못 입력한 경우
				System.out.println("다시 입력하세요 "); 
				break; //default 탈출 => 재입력
			}
			
			if(user.mainMenu == 7)
				break; //프로그램 종료
		}
	}
}
