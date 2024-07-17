import { setupWorker } from "msw/browser";
import missionHandler from "@/mocks/handler/missionHandler";

export const worker = setupWorker(...missionHandler);
