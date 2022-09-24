import { useEffect, useState } from 'react';
import { SpendingTarget,  UserSettings, IMonthlyIncome, TransactionImportanceTypes, BFFPayload, ITransaction } from '../../types';
import { RecordsTable } from '../../components/RecordsTable';
import { ResponsiveContainer, BarChart, CartesianGrid, XAxis, YAxis, Tooltip, Legend, Bar } from 'recharts';
import { useMoney } from '../../hooks';

export const DEFAULT_SETTINGS: UserSettings = {
  locale: 'pt-PT',
  currency: {
    symbol: '€',
    type: 'EUR',
  },
};

export const App: React.FC = () => {
  const [settings] = useState(DEFAULT_SETTINGS)
  const [spendingTargets, setSpendingTargets] = useState<SpendingTarget[]>([]);
  const [transactions, setTransactions] = useState<ITransaction[]>([]);
  const [monthlyIncomes, setMonthlyIncomes] = useState<IMonthlyIncome[]>([]);

  // useAuth();

  const money = useMoney(settings);

  useEffect(() => {
    void (async () => {
      const res = (await fetch(`/spending-targets`)) as Response;
      const data = await res.json() as SpendingTarget[];
      setSpendingTargets(data);
    })();

    void (async () => {
      const res = (await fetch(`/bff/web`)) as Response;
      const data = await res.json() as BFFPayload;
      setMonthlyIncomes(data.monthlyIncomes);
      setTransactions(data.transactions);
    })();
  }, []);

  const { currency } = settings;

  const data = spendingTargets.map((t) => ({
    color: t.color,
    current: parseFloat(String(t.current)).toFixed(2),
    allowed: parseFloat(String(t.total)).toFixed(2),
    name: t.category.toLowerCase(),
  }));

  const currentMonth = new Date().toLocaleString('default', { month: 'long' });
  const monthIncomesAmount = monthlyIncomes.reduce((acc, mi) => acc + mi.amount, 0);

  const monthExpenseAmount = spendingTargets.reduce((total, curr) => {
    if (curr.category !== TransactionImportanceTypes.SAVINGS) return total + curr.current;
    return total;
  }, 0);

  const totalMonthIncome = monthIncomesAmount;

  return (
    <div>
      <h1 className='text-3xl dark:text-white mb-10 mt-4'>
        <b className='text-blue-600'>
          {currentMonth}</b> Overview
      </h1>
      <div className='w-full mb-8'>
        <h3 className='text-xl'><b>Cashflow</b></h3>
        <div className='flex w-full justify-center items-center'>
          <p className='mr-4'>Total Earned</p>
          <p className='text-green-600'>+{money.format(totalMonthIncome)}</p>
        </div>
        <div className='flex w-full justify-center items-center'>
          <p className='mr-4'>Total Spent</p>
          <p className='text-red-600'>-{money.format(monthExpenseAmount)}</p>
        </div>
      </div>

      <div className='w-full h-full flex justify-center text-left'>
        <ResponsiveContainer width='100%' height={430} className='flex justify-center w-full'>
          <BarChart
            width={400}
            height={1000}
            data={data}
            margin={{
              top: 5,
              right: 30,
              left: 20,
              bottom: 5,
            }}
          >
            <CartesianGrid strokeDasharray='3 3' />
            <XAxis dataKey='name' />
            <YAxis />
            <Tooltip />
            <Legend />
            <Bar dataKey='current' fill='#8884d8' unit={currency.symbol} />
            <Bar dataKey='allowed' fill='#82ca9d' unit={currency.symbol} />
          </BarChart>
        </ResponsiveContainer>

        <div className='w-1/3'>
          <h3 className='text-xl mb-4'>My Spending targets</h3>
          {spendingTargets.map(({ category, total, current, color }) => {
            const percentage = parseFloat(String((current * 100) / total)).toFixed(2);
            const maxedPercentage = Math.min(Number(percentage), 100);

            return (
              <div className='flex flex-col justify-center my-2 w-full' key={category}>
                <p className='w-1/2' style={{ color }}>{category}</p>
                <p className='w-1/2'>{money.format(current)}{' '}/<b>{' '} {money.format(total)}</b></p>

                <div className='w-full bg-gray-200 rounded-full dark:bg-gray-700'>
                  <div className='bg-blue-600 text-xs font-medium text-blue-100 text-center p-0.5 leading-none rounded-full'
                    style={{
                      width: `${maxedPercentage}%`,
                    }}> {percentage}%</div>
                </div>
              </div>
            )
          })}
        </div>
      </div>

      <RecordsTable
        records={transactions}
      />
    </div>
  );
}

