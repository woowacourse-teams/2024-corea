import { setupServer } from "msw/node";
import roomHandler from "@/mocks/handler/roomHandler";

export const server = setupServer(...roomHandler);
