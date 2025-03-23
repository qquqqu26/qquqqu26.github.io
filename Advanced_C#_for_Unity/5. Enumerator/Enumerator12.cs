using JetBrains.Annotations;
using System.Collections;
using Unity.PlasticSCM.Editor.WebApi;
using UnityEngine;

public class enumerator12 : MonoBehaviour
{
    public class MyList : IEnumerable
    {
        public IEnumerator GetEnumerator()
        {
            int[] num = new int[5] { 1, 2, 3, 4, 5};
            int index = -1;

            while(true)
            {
                ++index;

                if (index >= num.Length )
                {
                    print("yield break ¸¸³²");
                    yield break;
                }

                yield return num[index];
            }

        }

    }

    void Start()
    {
        MyList list = new MyList();
        foreach (int n in list)
            print(n);

    }
}
