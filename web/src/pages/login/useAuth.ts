import cookie from 'js-cookie';
import { useNavigate } from 'react-router-dom';

export const useAuth = () => {
  const isAuth = Boolean(cookie.get('at'));
  const navigate = useNavigate();

  if (!isAuth) {
    navigate('/login', { replace: true });
  }

  return { isAuth };
}