import { useState } from "react";
import { ModalOverlay } from "../../components/ModalOverlay";
import { ITransaction, TransactionImportanceTypes } from "../../types";
import { PostAddRecordPayload } from "./Records";

interface AddRecordModalProps {
  open?: boolean;
  onClose: () => void;
  transaction: ITransaction | null;
  onSubmit?: (payload: PostAddRecordPayload) => void;
}

export const RecordModal: React.FC<AddRecordModalProps> = ({
  open,
  transaction,
  onClose,
  onSubmit = () => null,
}) => {
  const [amount, setAmount] = useState(transaction?.amount || 0);
  const [importance, setImportance] = useState<TransactionImportanceTypes>(
    transaction?.importance || TransactionImportanceTypes.ESSENTIAL
  );
  const [title, setTitle] = useState(transaction?.title || '');
  const [category, setCategory] = useState(transaction?.category || '');


  const handleSubmit = () => onSubmit({
    amount,
    importance,
    title,
    category,
  });

  return (
    <>
      <ModalOverlay onClose={onClose} />

      <div className='z-10 absolute p-4 w-full max-w-md h-full md:h-auto top-1/2 left-1/2 transform -translate-x-1/2 -translate-y-1/2'>
        <div className='relative bg-white rounded-lg shadow dark:bg-gray-700'>
          <button type='button' className='absolute top-3 right-2.5 text-gray-400 bg-transparent hover:bg-gray-200 hover:text-gray-900 rounded-lg text-sm p-1.5 ml-auto inline-flex items-center dark:hover:bg-gray-800 dark:hover:text-white' onClick={onClose}>
            <svg className='w-5 h-5' fill='currentColor' viewBox='0 0 20 20' xmlns='http://www.w3.org/2000/svg'><path fill-rule='evenodd' d='M4.293 4.293a1 1 0 011.414 0L10 8.586l4.293-4.293a1 1 0 111.414 1.414L11.414 10l4.293 4.293a1 1 0 01-1.414 1.414L10 11.414l-4.293 4.293a1 1 0 01-1.414-1.414L8.586 10 4.293 5.707a1 1 0 010-1.414z' clip-rule='evenodd'></path></svg>
          </button>
          <div className='py-6 px-6 lg:px-8'>
            <h3 className='mb-4 text-xl font-medium text-gray-900 dark:text-white'>Add new record</h3>

            <div className='space-y-6'>
              <div>
                <label htmlFor='title' className='block mb-2 text-sm font-medium text-gray-900 dark:text-gray-300'>Title</label>
                <input
                  name='title'
                  id='title'
                  value={title}
                  onChange={({ target: { value } }) => setTitle(value)}
                  className='bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-600 dark:border-gray-500 dark:placeholder-gray-400 dark:text-white'
                  placeholder='Some title...'
                />
              </div>

              <div>
                <label htmlFor='amount' className='block mb-2 text-sm font-medium text-gray-900 dark:text-gray-300'>Your amount</label>
                <input
                  type='number'
                  name='amount'
                  value={amount}
                  onChange={({ target: { value } }) => setAmount(Number(value))}
                  id='amount'
                  placeholder='42'
                  className='bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-600 dark:border-gray-500 dark:placeholder-gray-400 dark:text-white'
                />
              </div>

              <div>
                <label htmlFor='budgetCategoryType' className='block mb-2 text-sm font-medium text-gray-900 dark:text-gray-300'>Importance</label>
                <input
                  name='budgetCategoryType'
                  id='budgetCategoryType'
                  value={importance}
                  onChange={({ target: { value } }) => setImportance(value.toUpperCase() as TransactionImportanceTypes)}
                  placeholder='Needs'
                  className='bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-600 dark:border-gray-500 dark:placeholder-gray-400 dark:text-white'
                />
              </div>

              <div>
                <label htmlFor='category' className='block mb-2 text-sm font-medium text-gray-900 dark:text-gray-300'>Category</label>
                <input
                  name='category'
                  id='category'
                  value={category}
                  onChange={({ target: { value } }) => setCategory(value)}
                  placeholder='Category'
                  className='bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-600 dark:border-gray-500 dark:placeholder-gray-400 dark:text-white'
                />
              </div>

              <button
                onClick={handleSubmit}
                className='w-full text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800'
              >
                {transaction?.id ? "Edit" : "Create new"} record
              </button>
            </div>
          </div>
        </div>
      </div>
    </>
  );
};
