export type Method = "GET" | "POST" | "PUT" | "PATCH" | "DELETE";

export interface ApiProps {
  endpoint: string;
  headers?: Record<string, string>;
  body?: object | null;
  errorMessage?: string;
}

export interface RequestProps extends ApiProps {
  method: Method;
}

export interface QueueItem {
  resolve: (value: string | PromiseLike<string>) => void;
  reject: (reason?: Error) => void;
}
