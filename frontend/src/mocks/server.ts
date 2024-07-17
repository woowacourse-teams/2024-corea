import { setupServer } from "msw/node";
import missionHandler from "@/mocks/handler/missionHandler";

export const server = setupServer(...missionHandler);
