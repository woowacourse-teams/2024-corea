import styled from "styled-components";
import media from "@/styles/media";

export const SideNavBarContainer = styled.div`
  display: none;

  ${media.small`
    display: flex;
    gap: 1rem;
  `}
`;
