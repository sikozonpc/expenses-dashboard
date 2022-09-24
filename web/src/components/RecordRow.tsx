import { useMoney } from '../hooks';
import { DEFAULT_SETTINGS } from '../pages/app/App';
import { ITransaction, TransactionImportanceTypes } from '../types';

interface RecordProps {
  transaction: ITransaction;
  onEditRecordClick?: () => void;
  onDelete?: () => void;
}

export const RecordRow: React.FC<RecordProps> = ({
  transaction,
  onEditRecordClick,
  onDelete,
  ...rest
}) => {
  const money = useMoney(DEFAULT_SETTINGS);
  const isSaving = transaction.importance === TransactionImportanceTypes.SAVINGS;

  const getColorFromImportance = (importance: TransactionImportanceTypes) => {
    switch (importance) {
      case TransactionImportanceTypes.ESSENTIAL: return '#FDA172';
      case TransactionImportanceTypes.HAVE_TO_HAVE: return '#48AAAD';
      case TransactionImportanceTypes.NICE_TO_HAVE: return '#48AAAD';
      case TransactionImportanceTypes.SAVINGS: return '#00A86B';
      case TransactionImportanceTypes.SHOULD_NOT_HAVE: return 'red';
      default: return 'rgb(100, 116, 139)';
    }
  };

  const color = getColorFromImportance(transaction.importance)

  return (
    <tr className='bg-white border-b dark:bg-gray-800 dark:border-gray-700' {...rest}>

      <th scope='row' className='px-6 py-4 font-medium text-gray-900 dark:text-white whitespace-nowrap'>
        {transaction.title}
      </th>
      <td className='px-6 py-4'>
        {isSaving ? '+' : '-'} {money.format(Number(transaction.amount))}
      </td>
      <td className='px-6 py-4' style={{ color }}>
        {transaction.importance}
      </td>
      <td className='px-6 py-4'>
        {new Date(transaction.createdAt).toLocaleString()}
      </td>
      <td className='px-6 py-4 text-right'>
        {onEditRecordClick && <span
          className='font-medium text-blue-600 dark:text-blue-500 hover:underline mr-4 cursor-pointer'
          onClick={onEditRecordClick}
        >
          Edit
        </span>}
        {onDelete && <span
          className='font-medium text-red-600 dark:text-red-500 hover:underline cursor-pointer'
          onClick={onDelete}
        >
          Delete
        </span>}
      </td>
    </tr>
  );
}