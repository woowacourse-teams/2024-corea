import Button from "../../button/Button";
import Icon from "../../icon/Icon";
import SideNavBar from "../SideNavBar/SideNavBar";
import * as S from "./MobileHeader.style";
import React, { useState } from "react";

const MobileHeader = () => {
  const [isSideNavOpen, setIsSideNavOpen] = useState(false);

  const toggleSideNav = () => {
    setIsSideNavOpen((prev) => !prev);
  };

  return (
    <>
      <S.SideNavBarContainer>
        <Button onClick={toggleSideNav} size="xSmall" variant="default" aria-label="메뉴">
          <Icon kind="menu" size="2.6rem" />
        </Button>
      </S.SideNavBarContainer>

      <SideNavBar isOpen={isSideNavOpen} onClose={toggleSideNav} />
    </>
  );
};

export default MobileHeader;
