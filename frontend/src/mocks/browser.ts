import { setupWorker } from "msw/browser";
import roomHandler from "@/mocks/handler/roomHandler";

export const worker = setupWorker(...roomHandler);
