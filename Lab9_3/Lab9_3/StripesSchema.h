#pragma once

#include <tbb/parallel_for.h>

class StripesSchema
{
public:
	StripesSchema(double** A, double** B, int rows, int columns, int threadsNumber);
	double** calculateProduct();

private:
	double** A;
	double** B;

	double** C;

	int rows;
	int columns;

	int threadsNumber;

	void compute(int rowStart, int rowEnd, int columnStart, int columnEnd);
	double calculateEntry(int i, int j);
};

