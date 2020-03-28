using System;
using System.Collections.Generic;

namespace Lab9_5
{
    class Program
    {
        static void Main(string[] args)
        {
            var test=new Test("./resources/index.html");
            test.AddTask(100);
            test.AddTask(500);
            test.AddTask(1000);
            test.AddTask(1500);
            test.AddTask(2000);
            test.AddTask(2500);
            test.AddTask(3000);
            test.Run();
        }
    }
}