import { ITransaction } from '../types';
import { RecordRow } from './RecordRow';

interface RecordsTableProps {
  records: ITransaction[];
  onEditRecordClick?: (id: number) => void;
  onDelete?: (id: number) => void;
}

export const RecordsTable: React.FC<RecordsTableProps> = ({
  records,
  onEditRecordClick,
  onDelete,
}) => {
  return (
    <div className="relative overflow-x-auto shadow-md sm:rounded-lg">
      <table className="w-full text-sm text-left text-gray-500 dark:text-gray-400">
        <thead className="text-xs text-gray-700 uppercase bg-gray-50 dark:bg-gray-700 dark:text-gray-400">
          <tr>
            <th scope="col" className="px-6 py-3">
              Title
            </th>
            <th scope="col" className="px-6 py-3">
              Amount
            </th>
            <th scope="col" className="px-6 py-3">
              Budget Category
            </th>
            <th scope="col" className="px-6 py-3">
              Created At
            </th>
            <th scope="col" className="px-6 py-3">
              <span className="sr-only">Edit</span>
            </th>
          </tr>
        </thead>
        <tbody>
          {records.map((r) => (
            <RecordRow
              key={r.id}
              transaction={r}
              onEditRecordClick={onEditRecordClick ? () => onEditRecordClick(r.id) : undefined}
              onDelete={onDelete ? () => onDelete(r.id) : undefined}
            />))}
        </tbody>
      </table>
    </div>

  );
}