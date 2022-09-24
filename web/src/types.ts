export enum TransactionImportanceTypes {
  BLANK = "blank",
  // Regretted expenses, it happens.
  SHOULD_NOT_HAVE = "SHOULD_NOT_HAVE",
  // Expenses that you can live without but you wanted.
  NICE_TO_HAVE = "NICE_TO_HAVE",
  // Expenses that your life would be way less enjoyable without.
  HAVE_TO_HAVE = "HAVE_TO_HAVE",
  // Essentials needs expenses.
  ESSENTIAL = "ESSENTIAL",
  SAVINGS = "SAVINGS"
}

export interface UserSettings {
  currency: {
    symbol: string;
    type: string;
  };
  locale: string;
}

export interface SpendingTarget {
  category: string;
  total: number;
  current: number;
  percentage: string;
  color: string;
}

export interface ITransaction {
  id: number;
  title: string;
  amount: number;
  category: string;
  importance: TransactionImportanceTypes;
  createdAt: string;
}

export interface IMonthlyIncome {
  id: number;
  title: string;
  amount: number;
  isRecurring: boolean;
  createdAt: string;
}

export interface BFFPayload {
  monthlyIncomes: IMonthlyIncome[];
  transactions: ITransaction[];
}