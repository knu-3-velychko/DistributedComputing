using  System;
using System.Linq;
using System.Threading;
using System.Threading.Tasks;

namespace Lab9_5
{
    public class StripesSchema
    {
        private readonly double[,] _A;
        private readonly double[,] _B;

        private double[,] _C;

        private readonly int _threadsNumber;

        public StripesSchema(double[,] A, double[,] B,int threadsNumber)
        {
            this._A = A;
            this._B = B;
            this._C=new double[A.Length,B.GetLength(0)];
            this._threadsNumber = threadsNumber;
        }

        public double[,] CalculateProduct()
        {
            var taskSize = _A.Length / _threadsNumber;

            for (var i = 0; i < _threadsNumber; i++)
            {
                var i1 = i;
                Parallel.For(0, _threadsNumber, (j, status) =>
                {
                    var rowStart = j * taskSize;
                    var rowEnd = (j == _threadsNumber - 1) ? _A.Length : (j + 1) * taskSize;

                    var columnStart = ((i1 + j) % _threadsNumber) * taskSize;
                    var columnEnd = (columnStart / taskSize == _threadsNumber - 1) ? _A.Length : (columnStart + taskSize);

                    Compute(rowStart,rowEnd,columnStart,columnEnd);
                });
            }

            return _C;
        }

        private void Compute( int rowStart, int rowEnd, int columnStart,int columnEnd)
        {
            for (var i = rowStart; i < rowEnd; i++)
            {
                for (var j = columnStart; j < columnEnd; j++)
                {
                    _C[i, j] = CalculateEntry(i, j);
                }
            }
        }

        private double CalculateEntry(int i, int j)
        {
            return _B.Cast<double>().Select((t, k) => _A[i, k] * _B[k, j]).Sum();
        }
        
    }
}