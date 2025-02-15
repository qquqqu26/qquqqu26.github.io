/*
 * 무한 루프에서 벗어나기 위해 코드를 완전히 작성함 
 */
using JetBrains.Annotations;
using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class test : MonoBehaviour
{

    public class MyList
    {
        public enumerator GetEnumerator()
        {
            enumerator enumerator = new enumerator();
            return enumerator; 
        }

        public class enumerator
        {
            //보충한 부분
            int[] num = new int[5] {1, 2, 3, 4, 5}; //순회할 배열
            int index = -1;             //순회시킬 인덱스

            //Current 속성: 현재 index가 참조하는 배열값 반환
            public object Current { get { return num[index]; } }

            //MoveNext() 메서드: 
            //index 다음 배열값에 접근 할 수 있는 지
            public bool MoveNext()
            {
                if (index == num.Length-1) return false;

                //접근 가능하면 index를 다음 배열가리키게 
                index++; 
                return index < num.Length;
            }
        }
    }
    void Start()
    {
        MyList myList = new MyList();
        foreach (var item in myList) 
        {
            Debug.Log(item);
        }
    }

}
