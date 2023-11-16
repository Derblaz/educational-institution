export interface User {
  id?: { value: string };
  name: string;
  username: string;
  profile: string;
  password: string;
  isActive?: boolean;
  createdAt?: string;
  updatedAt?: string;
  deletedAt?: string | null;
}

export interface Search {
  search?: string;
  page?: number;
  perPage?: number;
  sort?: string;
  dir?: string;
}

export interface Pagination<T> {
  currentPage: number;
  perPage: number;
  total: number;
  items: T[];
}
