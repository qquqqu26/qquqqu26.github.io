/*
 * ���� �������� ����� ���� �ڵ带 ������ �ۼ��� 
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
            //������ �κ�
            int[] num = new int[5] {1, 2, 3, 4, 5}; //��ȸ�� �迭
            int index = -1;             //��ȸ��ų �ε���

            //Current �Ӽ�: ���� index�� �����ϴ� �迭�� ��ȯ
            public object Current { get { return num[index]; } }

            //MoveNext() �޼���: 
            //index ���� �迭���� ���� �� �� �ִ� ��
            public bool MoveNext()
            {
                if (index == num.Length-1) return false;

                //���� �����ϸ� index�� ���� �迭����Ű�� 
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
