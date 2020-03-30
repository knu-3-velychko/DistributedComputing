#include "Test.h"

Test::Test(std::string path)
{
	builder = new HtmlBuilder(path);
	builder->createHtml()->addHead()->startBody()->createTable();

	sizes = std::vector<int>();
}

void Test::addTask(int size)
{
	sizes.push_back(size);
}

void Test::run()
{
	double sequentialTime;
	double time, acceleration;

	for (auto i : sizes) {
		std::vector<std::pair<double, double>> results = std::vector<std::pair<double, double>>();
		sequentialTime = calculate(i, 1) / 1000.0;

		time = calculate(i, 2) / 1000.0;
		acceleration = sequentialTime / time;
		results.push_back(std::pair<double, double>(time, acceleration));

		time = calculate(i, 4) / 1000.0;
		acceleration = sequentialTime / time;
		results.push_back(std::pair<double, double>(time, acceleration));

		builder->addResult(i, sequentialTime, results);

		std::cout << i << std::endl;
	}
}

void Test::finish()
{
	builder->finishTable()->finishBody()->finishHtml();
}
