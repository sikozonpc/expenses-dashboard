import { UserSettings } from "../types";

export const useMoney = (settings: UserSettings) => {
  const formatter = new Intl.NumberFormat(settings.locale, {
    maximumFractionDigits: 2,
    currency: settings.currency.type,
    style: 'currency',
  });

  return formatter;
}