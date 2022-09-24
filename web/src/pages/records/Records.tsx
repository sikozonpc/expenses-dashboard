import { useEffect, useState } from 'react';
import { RecordsTable } from '../../components/RecordsTable';
import { ITransaction, TransactionImportanceTypes } from '../../types';
import { RecordModal } from './RecordModal';

export interface PostAddRecordPayload {
  title: string;
  amount: number;
  importance: TransactionImportanceTypes;
  category?: string;
}

export const RecordsPage: React.FC = () => {
  const [transactions, setTransactions] = useState<ITransaction[]>([]);
  const [editRecord, setEditRecord] = useState<ITransaction | null>(null);
  const [isAddRecordModalOpen, setIsAddRecordModalOpen] = useState(false);
  const currentMonth = new Date().toLocaleString('default', { month: 'long' });

  useEffect(() => {
    void (async () => {
      const res = (await fetch("/transactions")) as Response;
      const data = await res.json() as ITransaction[];
      setTransactions(data);
    })();
  }, []);

  const submitAddRecord = (payload: PostAddRecordPayload) => {
    if (editRecord?.id) {
      void (async () => {
        const res = await fetch(`/transactions/${editRecord.id}`, {
          method: 'PUT',
          body: JSON.stringify(payload),
          headers: {
            'Content-type': 'application/json; charset=UTF-8'
          }
        });
        const data = await res.json() as ITransaction;

        const newRecords = transactions.map((r) => {
          if (r.id === editRecord.id) {
            return data;
          }
          return r;
        })

        setIsAddRecordModalOpen(false);
        setTransactions(newRecords)
      })();
      return;
    }

    void (async () => {
      const res = await fetch("/transactions", {
        method: 'POST',
        body: JSON.stringify(payload),
        headers: {
          'Content-type': 'application/json; charset=UTF-8'
        }
      });
      const data = await res.json() as ITransaction;

      setIsAddRecordModalOpen(false);
      setTransactions([data, ...transactions])
    })();
  };

  const handleEditRecordOpen = (id: number) => {
    setIsAddRecordModalOpen(true);
    setEditRecord(transactions.find((r) => r.id === id) ?? null);
  };

  const handleDeleteRecord = (id: number) => {
    void (async () => {
      await fetch(`/transactions/${id}`, {
        method: 'DELETE',
        headers: {
          'Content-type': 'application/json; charset=UTF-8'
        }
      });

      setTransactions(transactions.filter((r) => r.id !== id))
    })();
  };

  return (
    <div className='text-left'>
      <h3 className='text-3xl my-8'><b className='text-blue-600 dark:text-blue-300'>{`${currentMonth}`}</b> Records</h3>

      <button
        type='button'
        onClick={() => setIsAddRecordModalOpen(!isAddRecordModalOpen)}
        className='text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 mr-2 dark:bg-blue-600 dark:hover:bg-blue-700 focus:outline-none dark:focus:ring-blue-800 mb-4'
      >
        Add Record
      </button>

      <RecordsTable
        records={transactions}
        onEditRecordClick={handleEditRecordOpen}
        onDelete={handleDeleteRecord}
      />

      {isAddRecordModalOpen && (
        <RecordModal
          transaction={editRecord}
          open={isAddRecordModalOpen}
          onClose={() => {
            setIsAddRecordModalOpen(false);
            setEditRecord(null);
          }}
          onSubmit={submitAddRecord}
        />)}
    </div>
  );
}