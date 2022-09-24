import { NavLink } from "react-router-dom";

interface SidebarRowProps extends React.HTMLAttributes<HTMLDivElement> {
  to: string;
  text: string;
  svgIcon?: React.ReactElement;
}

export const SidebarRow: React.FC<SidebarRowProps> = ({
  to,
  text,
  svgIcon,
  ...rest
}) => {
  return (
    <li>
      <NavLink
        key={to}
        style={({ isActive }) => ({ opacity: isActive ? '0.6' : '1' })}
        to={to}
        className='flex items-center p-2 text-base font-normal text-gray-900 rounded-lg dark:text-white hover:bg-gray-100 dark:hover:bg-gray-700'
      >
        {svgIcon}
        <span className='ml-3'>
          {text}
        </span>
      </NavLink>
    </li>
  );
}