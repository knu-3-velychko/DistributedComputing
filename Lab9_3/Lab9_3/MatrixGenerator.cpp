#include "MatrixGenerator.h"

MatrixGenerator::MatrixGenerator(int size, double maxValue) :MatrixGenerator(size, size, maxValue)
{
}

MatrixGenerator::MatrixGenerator(int rowSize, int columnSize, double maxValue) : rowSize(rowSize), columnSize(columnSize), maxValue(maxValue)
{
}

double** MatrixGenerator::generate()
{
	double** matrix = new double* [rowSize];
	return nullptr;
}
