import java.util.GregorianCalendar;
import java.io.*;

public class Manager {

	//1. 필드
	private int maxOfArr;// = 100; //배열 최대 크기
	private Schedule[] schedules = new Schedule[maxOfArr];  //일정 저장 배열
	private int indices[]; //인덱스 저장 배열
	private int schedulesSize = 0; //전체 일정 개수
	private int indicesSize = 0; //(인수로 받은) 배열의 일정 개수
	//생성자
	Manager () { //1. 기본 생성자 (배열 크기 기본 100으로 설정)
		schedules = new Schedule[100]; 
	}
	
	Manager (int size) { //2-2. 인수가 배열 크기인 생성자
		maxOfArr = size;
		schedules = new Schedule[maxOfArr]; 
	}
	
	//메인 메뉴 메서드
	//1. 일정 추가
	void add(Schedule s) {
		if(schedulesSize < maxOfArr) {
			
			schedules[schedulesSize] = s;	
			schedules[schedulesSize].setSerialNumber(++schedulesSize);
			
		}
	}
	
	//2. 일정 검색
		//1. 날짜 검색: 해당 날짜(년월일, 년/월, 년) 이후 일정들 반환
	int[] search(GregorianCalendar g) {
		indices = new int[schedulesSize];
		indicesSize = 0;
		
		for(int i = 0; i < schedulesSize; i++) {
			if( schedules[i].getStart().compareTo(g) > 0) {
				indices[indicesSize++] = i;
			}
		}
		return indices;
	}
	
		//2. 기간 검색
	int[] search(GregorianCalendar start, GregorianCalendar end) {
		indices = new int[schedulesSize];
		indicesSize = 0;
		
		for(int i = 0; i < schedulesSize ; i++) {
			if( schedules[i].getStart().compareTo(start) >= 0 
					&& schedules[i].getEnd().compareTo(end) <= 0) {
				indices[indicesSize++] = i;
			}
		}
		return indices;
	}
	
		//3. 키워드 검색
	int[] search(String keyword) {
		indices = new int[schedulesSize];
		indicesSize = 0;
		
		for(int i = 0; i < schedulesSize; i++) {
			
			if(schedules[i].getLabel().contains(keyword) 
					|| schedules[i].getMemo().contains(keyword)) {
				indices[indicesSize++] = i;
			}
		}
		return indices;
	}
	
		//4. 우선순위 검색
	int[] search(int importance) {
		indices = new int[schedulesSize];
		indicesSize = 0;
		
		for(int i = 0; i < schedulesSize; i++) {
			
			if(schedules[i].getImportance() == importance) {
				indices[indicesSize++] = i;
			}
		}
		return indices;
	}
	
		//5. 카테고리 검색
	int[] search(char category) {
		indices = new int[schedulesSize];
		indicesSize = 0;
		
		for(int i = 0; i < schedulesSize; i++) {
			if(schedules[i].getCategory() == category) {
				indices[indicesSize++] = i;
			}
		}
		return indices;
	}
	
	//3.  일정 삭제
	void delete(int index) throws Exception { //단일 삭제
		
		//인덱스 범위내에 있는 지 확인,예외처리
		if(index < 0 || index > schedulesSize) {
			throw new Exception("0보다 크고 "+ 
					(schedulesSize-1) +"보다 작은 값을 입력하세요." );
		}
		
		for(int i = index; i < schedulesSize; i++) {
			schedules[i] = schedules[i+1];
			
			if(i == maxOfArr - 1) { //마지막 인덱스 null
				schedules[i] = null;
			}
		}
		schedulesSize--;
		
	}
	
	//4. 일정 수정
	void edit(int index, Schedule s) throws Exception {
		
		//인덱스 범위내에 있는 지 확인,예외처리
		if(index < 0 || index > schedulesSize) {
			throw new Exception("편집 오류: 0보다 크고 " + 
					(schedulesSize-1) +"보다 작은 값을 입력하세요." );
		}
				
		schedules[index] = s;
		schedules[index].setSerialNumber(index + 1);
	}
	
	//3-5. getter & setter
	Schedule[] getSchedules() {
		return schedules;
	}
	
	int getSchedulesSize() {
		return schedulesSize;
	}
	
	int[] getIndices() {
		return indices;
	}
	
	int getIndicesSize() {
		return indicesSize;
	}
	
	//3-6. 기타 메서드
	boolean duplicateCheck(Schedule s) {
		for(int i = 0; i < schedulesSize; i++) {
			if( s.equals(schedules[i]) )
				return true;
		}
		return false;
	}
	
	void writeSchedules (ObjectOutputStream out) throws Exception {
		
		try {
			out.writeInt(schedulesSize);  // 스케줄 배열의 크기를 저장
			for (int i = 0; i < schedulesSize; i++) {
				out.writeObject(schedules[i]);
			}
		} catch (EOFException eofe) {
			//파일 끝
		} catch (IOException ioe) {
			throw new IOException(ioe.getMessage());
			
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		finally {
		
			try {
				out.close();
			}
			catch (Exception e) {
				throw new Exception(e.getMessage());
			}
		}
	}
	
	void readScheudules (ObjectInputStream in) throws Exception {
		try {
			schedulesSize = in.readInt(); // 스케줄 사이즈 읽기
			schedules = new Schedule[maxOfArr];
			for(int i = 0; i < schedulesSize; i++) {
				schedules[i] = (Schedule) in.readObject();
			}
		} catch (EOFException eofe) { //파일 끝
        	
		} catch (FileNotFoundException fnfe) {
			throw new FileNotFoundException("readSchedules 오류: 파일이 존재하지 않음");
        } catch (ClassNotFoundException cnfe) {
        	throw new ClassNotFoundException("readSchedules 오류: 클래스가 존재하지 않음");
        } catch (IOException ioe) {
        	throw new IOException(ioe.getMessage());
        } catch (Exception e) {
        	throw new Exception(e.getMessage());
        } 
		finally {
			try {
				in.close();
			}
			catch (Exception e) {
				throw new Exception(e.getMessage());
			}
        }
	}
	
}
