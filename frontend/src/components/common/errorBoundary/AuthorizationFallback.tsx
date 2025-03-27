import AlertModal from "../modal/alertModal/AlertModal";
import React, { useEffect } from "react";
import useModal from "@/hooks/common/useModal";
import Banner from "@/components/main/banner/Banner";
import { githubAuthUrl } from "@/config/githubAuthUrl";
import MESSAGES from "@/constants/message";

const AuthorizationFallback = () => {
  const { isModalOpen, handleOpenModal, handleCloseModal } = useModal();

  useEffect(() => {
    const alreadyHandled = sessionStorage.getItem("auth_fallback_handled");

    if (!alreadyHandled) {
      sessionStorage.setItem("auth_fallback_handled", "true");
      localStorage.clear();
      handleOpenModal();
    }
  }, []);

  const handleRedirect = (url: string) => {
    sessionStorage.removeItem("auth_fallback_handled");
    handleCloseModal();
    window.location.href = url;
  };

  return (
    <>
      <AlertModal
        isOpen={isModalOpen}
        onClose={() => handleRedirect("/")}
        onConfirm={() => handleRedirect(githubAuthUrl)}
      >
        {MESSAGES.ERROR.POST_REFRESH}
      </AlertModal>
      <Banner />
    </>
  );
};

export default AuthorizationFallback;
