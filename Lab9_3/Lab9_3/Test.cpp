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
		sequentialTime = calculate(i, 1);

		time = calculate(i, 2);
		acceleration = sequentialTime / time;
		results.push_back(std::pair<double, double>(time, acceleration));

		time = calculate(i, 4);
		acceleration = sequentialTime / time;
		results.push_back(std::pair<double, double>(time, acceleration));

		builder->addResult(i, sequentialTime, results);

		std::cout << i << std::endl;
	}
}

double Test::calculate(int size, int threadsNumber)
{
	auto matrixGenerator = MatrixGenerator(size, 100.0);
	auto A = matrixGenerator.generate();
	auto B = matrixGenerator.generate();

	auto stripesShema = StripesSchema(A, B, size, size, threadsNumber);

	auto start = std::chrono::steady_clock::now();

	auto C = stripesShema.calculateProduct();

	auto end = std::chrono::steady_clock::now();
	std::chrono::duration<double> elapsedSeconds = end - start;

	return elapsedSeconds.count();
}

void Test::finish()
{
	builder->finishTable()->finishBody()->finishHtml();
}
