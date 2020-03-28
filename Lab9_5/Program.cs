using System;

namespace Lab9_5
{
    class Program
    {
        static void Main(string[] args)
        {
            var htmlBuilder = new HtmlBuilder("./resources/index.html");
            htmlBuilder.CreateHtml().AddHead().CreateTable().FinishTable().Finish();
        }
    }
}