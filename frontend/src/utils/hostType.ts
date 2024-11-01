const hostname = window.location.hostname;

const getHostType = () => {
  if (process.env.NODE_ENV === "development") return "develop";

  if (hostname.includes("dev")) return "release";

  return "production";
};

export const hostType = getHostType();
