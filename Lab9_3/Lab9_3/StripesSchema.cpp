#include "StripesSchema.h"

StripesSchema::StripesSchema(double** A, double** B, int rows, int columns, int threadsNumber) :A(A), B(B), rows(rows), columns(columns), threadsNumber(threadsNumber)
{
	C = new double* [rows];
	for (int i = 0; i < rows; i++) {
		C[i] = new double[columns];
	}
}

double** StripesSchema::calculateProduct()
{
	int taskSize = rows / threadsNumber;
	/*
	for (int i = 0; i < threadsNumber; i++) {
		tbb::parallel_for(tbb::blocked_range<int>(0, threadsNumber), [&](tbb::blocked_range<int> r) {

			}

	}
	*/

	return nullptr;
}

void StripesSchema::compute(int rowStart, int rowEnd, int columnStart, int columnEnd)
{
}

double StripesSchema::calculateEntry(int i, int j)
{
	return 0.0;
}

