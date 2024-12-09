import styled from "styled-components";
import media from "@/styles/media";

export const Layout = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
`;

export const AlarmList = styled.ul`
  ${media.small`
    width: 100%;
  `}

  ${media.medium`
    width: 100%;
    min-width: 590px;
    max-width: 800px;
  `}

  ${media.large`
    width: 800px;
  `}
`;
