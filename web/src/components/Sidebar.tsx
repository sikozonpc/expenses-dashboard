import { SidebarRow } from "./SidebarRow";

interface SideBarProps extends React.HTMLAttributes<HTMLDivElement> { }

export const Sidebar: React.FC<SideBarProps> = ({ ...rest }) => {
  
  return (
    <aside {...rest} aria-label='Sidebar'>
      <div className='overflow-y-auto py-4 px-3 bg-gray-50 rounded dark:bg-gray-800 h-full'>
        <ul className='space-y-2'>
          <SidebarRow
            text='Dashboard'
            to='/'
            svgIcon={<svg className='w-6 h-6 text-gray-500 transition duration-75 dark:text-gray-400 group-hover:text-gray-900 dark:group-hover:text-white' fill='currentColor' viewBox='0 0 20 20' xmlns='http://www.w3.org/2000/svg'><path d='M2 10a8 8 0 018-8v8h8a8 8 0 11-16 0z'></path><path d='M12 2.252A8.014 8.014 0 0117.748 8H12V2.252z'></path></svg>}
          />
          <SidebarRow
            text='Records'
            to='/records'
            svgIcon={<svg className='flex-shrink-0 w-6 h-6 text-gray-500 transition duration-75 dark:text-gray-400 group-hover:text-gray-900 dark:group-hover:text-white' fill='currentColor' viewBox='0 0 20 20' xmlns='http://www.w3.org/2000/svg'><path fill-rule='evenodd' d='M5 4a3 3 0 00-3 3v6a3 3 0 003 3h10a3 3 0 003-3V7a3 3 0 00-3-3H5zm-1 9v-1h5v2H5a1 1 0 01-1-1zm7 1h4a1 1 0 001-1v-1h-5v2zm0-4h5V8h-5v2zM9 8H4v2h5V8z' clip-rule='evenodd'></path></svg>
            }
          />
          <SidebarRow
            text='Subscriptions'
            to='/subscriptions'
            svgIcon={<svg className='flex-shrink-0 w-6 h-6 text-gray-500 transition duration-75 dark:text-gray-400 group-hover:text-gray-900 dark:group-hover:text-white' fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M17 9V7a2 2 0 00-2-2H5a2 2 0 00-2 2v6a2 2 0 002 2h2m2 4h10a2 2 0 002-2v-6a2 2 0 00-2-2H9a2 2 0 00-2 2v6a2 2 0 002 2zm7-5a2 2 0 11-4 0 2 2 0 014 0z"></path></svg>}
          />
          <SidebarRow
            text='Monthly Incomes'
            to='/monthly-incomes'
            svgIcon={<svg className="flex-shrink-0 w-6 h-6 text-gray-500 transition duration-75 dark:text-gray-400 group-hover:text-gray-900 dark:group-hover:text-white" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M14.121 15.536c-1.171 1.952-3.07 1.952-4.242 0-1.172-1.953-1.172-5.119 0-7.072 1.171-1.952 3.07-1.952 4.242 0M8 10.5h4m-4 3h4m9-1.5a9 9 0 11-18 0 9 9 0 0118 0z"></path></svg>}
          />
          <SidebarRow
            text='Budget Rule'
            to='/budget-rule'
            svgIcon={<svg className='flex-shrink-0 w-6 h-6 text-gray-500 transition duration-75 dark:text-gray-400 group-hover:text-gray-900 dark:group-hover:text-white' fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M3 6l3 1m0 0l-3 9a5.002 5.002 0 006.001 0M6 7l3 9M6 7l6-2m6 2l3-1m-3 1l-3 9a5.002 5.002 0 006.001 0M18 7l3 9m-3-9l-6-2m0-2v2m0 16V5m0 16H9m3 0h3"></path></svg>}
          />
        </ul>
      </div>
    </aside>
  );
};
