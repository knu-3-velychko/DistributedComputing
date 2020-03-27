using System;
using System.Collections.Generic;

namespace Lab9_5
{
    public class Test
    {
        private List<int> sizes;
        private HtmlBuilder _builder;
        public Test(string path)
        {
            _builder= new HtmlBuilder(path);
            _builder.CreateHtml().AddHead().CreateTable();
            
            this.sizes=new List<int>();
        }

        public void AddTask(int size)
        {
            sizes.Add(size);
        }

        public void Run()
        {
            double sequentialTime;
            double time, acceleration;
            List<KeyValuePair<double, double>> results;

            foreach (var i in sizes)
            {
                results=new List<KeyValuePair<double, double>>();
                //TODO: time
            }
        }

        private void Finish()
        {
            _builder.FinishTable().Finish();
        }
    }
}