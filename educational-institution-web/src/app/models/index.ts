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

export interface Discipline {
  id?: { value: string };
  name?: string;
  credits?: number;
  description?: string;
  program?: string;
  presential?: boolean;
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

export interface Course {
  id?: { value: string };
  name?: string;
  description?: string;
  monthsDuration?: number;
  places?: number;
  semesters?: Discipline[][];
  isActive?: boolean;
  createdAt?: string;
  updatedAt?: string;
  deletedAt?: string | null;
}
