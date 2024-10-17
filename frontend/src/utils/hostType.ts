const getHostType = () => {
  const hostname = window.location.hostname;

  if (process.env.NODE_ENV === "development") return "develop";

  if (hostname.includes("dev")) return "release";

  return "production";
};

export const hostType = getHostType();
