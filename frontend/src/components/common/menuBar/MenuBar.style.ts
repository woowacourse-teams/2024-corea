import styled from "styled-components";
import media from "@/styles/media";

export const MenuBarContainer = styled.div`
  display: flex;
  gap: 3rem;
  align-items: center;
  justify-content: center;

  width: 100%;
  padding: 1rem;

  ${media.small`
    gap: 0.5rem;
  `}
`;

export const StyledChildren = styled.img`
  width: 32px;
`;
