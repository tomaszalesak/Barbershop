import { createContext, useContext } from 'react';

export type Role = 'ROLE_CUSTOMER' | 'ROLE_EMPLOYEE' | 'ROLE_ADMIN' | 'UNKNOWN';

export type StoreType = {
  id: string | null;
  login: string | null;
  role: Role;
  jwt: string | null;
};

export type StoreState = {
  user: StoreType;
  setUser: (user: StoreType) => void;
};

export const store = createContext<StoreState>({
  user: { id: null, login: null, role: 'UNKNOWN', jwt: null },
  setUser: () => {},
});

export const useStore = (): StoreState => useContext(store);
