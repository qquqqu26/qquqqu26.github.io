using System.Collections;
using Unity.PlasticSCM.Editor.WebApi;
using UnityEngine;

public class enumerator10 : MonoBehaviour
{
    public class MyList : IEnumerable
    {
        public IEnumerator GetEnumerator()
        {
            enumerator enumerator = new enumerator();
            return enumerator;
        }

        public class enumerator : IEnumerator
        {
            int[] num = new int[5] { 1, 2, 3, 4, 5 };
            int index = -1;
            public object Current { get { return num[index]; } }

            public bool MoveNext()
            {
                if (index == num.Length - 1)
                    return false;

                ++index;
                return (index < num.Length);
            }

            public void Reset()
            {
                index = -1;
            }
        }
    }

    void Start()
    {
        MyList list = new MyList();
        foreach (int n in list)
            print($"{n}");

    }
}
