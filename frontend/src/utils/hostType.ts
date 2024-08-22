const getHostType = () => {
  const hostname = window.location.hostname;

  if (hostname === "dev.code-review-area.com") {
    return "production";
  }
  if (hostname === "code-review-area.com") {
    return "release";
  }
  return "develop";
};

export const hostType = getHostType();
