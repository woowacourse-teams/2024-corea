export type Method = "GET" | "POST" | "PUT" | "PATCH" | "DELETE";

export interface QueueItem {
  resolve: (value: string | PromiseLike<string>) => void;
  reject: (reason?: Error) => void;
}
