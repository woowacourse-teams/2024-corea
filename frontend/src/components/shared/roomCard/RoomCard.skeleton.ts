import styled from "styled-components";
import { SkeletonShimmer } from "@/styles/common";
import media from "@/styles/media";

export const Wrapper = styled.div`
  width: 100%;
  border-radius: 15px;

  ${media.small`
    height: 271px;
  `}

  ${media.medium`
    height: 291px;
  `}

  ${media.large`
    height: 311px;
  `}

  ${SkeletonShimmer}
`;
