import authHandler from "./handler/authHandler";
import rankingHandler from "./handler/rankingHandler";
import alarmHandler from "@/mocks/handler/alarmHandler";
import feedbackHandler from "@/mocks/handler/feedbackHandler";
import profileHandler from "@/mocks/handler/profileHandler";
import roomHandler from "@/mocks/handler/roomHandler";

const handlers = [
  ...authHandler,
  ...roomHandler,
  ...profileHandler,
  ...feedbackHandler,
  ...rankingHandler,
  ...alarmHandler,
];

export default handlers;
